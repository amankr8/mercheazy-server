package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.entity.store.Store;
import com.mercheazy.server.entity.store.StoreOwner;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreOwnerRepository;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mercheazy.server.entity.store.StoreOwner.Role.CREATOR;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final UserService userService;
    private final StoreRepository storeRepository;
    private final StoreOwnerRepository storeOwnerRepository;

    @Override
    public Store addStore(StoreRequestDto storeRequestDto) {
        AuthUser currentAuthUser = AuthUtil.getLoggedInUser();
        if (storeOwnerRepository.findByAppUserId(currentAuthUser.getId()).isPresent()) {
            throw new IllegalArgumentException("User already has a store.");
        }

        Store store = Store.builder()
                .name(storeRequestDto.getName())
                .desc(storeRequestDto.getDesc())
                .storeOwners(new ArrayList<>())
                .build();
        store = storeRepository.save(store);

        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .authUser(currentAuthUser)
                .role(CREATOR)
                .build();

        store.getStoreOwners().add(storeOwner);
        return storeRepository.save(store);
    }


    @Override
    public Store updateStoreDetails(int id, StoreRequestDto storeRequestDto) {
        Store store = getStoreById(id);
        store.setName(storeRequestDto.getName());
        store.setDesc(storeRequestDto.getDesc());
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(int id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));
    }

    @Override
    public List<StoreOwner> getStoreOwnersByStoreId(int storeId) {
        return storeOwnerRepository.findByStoreId(storeId);
    }

    @Override
    public StoreOwner getStoreOwnerByStoreIdAndUserId(int storeId, int userId) {
        return storeOwnerRepository.findByStoreIdAndAppUserId(storeId, userId).orElse(null);
    }

    @Override
    public Store getStoreByUserId(int userId) {
        return storeOwnerRepository.findByAppUserId(userId).map(StoreOwner::getStore)
                .orElseThrow(() -> new ResourceNotFoundException("No store found for this user."));
    }

    @Override
    public void deleteStore(int id) {
        StoreOwner storeOwner = getStoreOwnerByStoreIdAndUserId(id, AuthUtil.getLoggedInUser().getId());
        if (storeOwner == null) {
            throw new IllegalArgumentException("No store found for this user.");
        }
        if (storeOwner.getRole() != CREATOR) {
            throw new IllegalArgumentException("You are unauthorized to delete this store.");
        }
        storeRepository.deleteById(id);
    }

    @Override
    public StoreOwner addStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = getStoreById(storeId);
        if (getStoreOwnerByStoreIdAndUserId(storeId, AuthUtil.getLoggedInUser().getId()) == null) {
            throw new IllegalArgumentException("You are not authorized to add store owners.");
        }
        if (getStoreOwnerByStoreIdAndUserId(storeId, storeOwnerRequestDto.getUserId()) != null) {
            throw new IllegalArgumentException("User is already a store owner.");
        }

        AuthUser authUser = userService.getUserById(storeOwnerRequestDto.getUserId());
        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .authUser(authUser)
                .role(storeOwnerRequestDto.getRole())
                .build();
        storeOwner = storeOwnerRepository.save(storeOwner);

        store.getStoreOwners().add(storeOwner);
        return storeOwner;
    }

    @Override
    public void removeStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = getStoreById(storeId);
        if (getStoreOwnerByStoreIdAndUserId(storeId, AuthUtil.getLoggedInUser().getId()) == null) {
            throw new IllegalArgumentException("You are not authorized to remove store owners.");
        }

        StoreOwner removableStoreOwner = getStoreOwnerByStoreIdAndUserId(storeId, storeOwnerRequestDto.getUserId());
        if (removableStoreOwner == null) {
            throw new IllegalArgumentException("User requested for removal is not a store owner.");
        }
        // Prevent removing the CREATOR
        if (removableStoreOwner.getRole() == CREATOR) {
            throw new IllegalArgumentException("The creator of the store cannot be removed.");
        }

        store.getStoreOwners().remove(removableStoreOwner);
        storeOwnerRepository.delete(removableStoreOwner);
    }
}
