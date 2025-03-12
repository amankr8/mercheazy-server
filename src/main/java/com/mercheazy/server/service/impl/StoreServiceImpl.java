package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.entity.store.Store;
import com.mercheazy.server.entity.store.StoreOwner;
import com.mercheazy.server.entity.user.AppUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreOwnerRepository;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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
        AppUser currentAppUser = AuthUtil.getLoggedInUser();
        if (storeOwnerRepository.findByAppUserId(currentAppUser.getId()).isPresent()) {
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
                .appUser(currentAppUser)
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
    public Store getStoreByUserId(int userId) {
        return storeOwnerRepository.findByAppUserId(userId).map(StoreOwner::getStore)
                .orElseThrow(() -> new ResourceNotFoundException("No store found for this user."));
    }

    @Override
    public void deleteStore(int id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreOwner addStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = getStoreById(storeId);
        if (!isUserStoreOwner(storeId, AuthUtil.getLoggedInUser().getId())) {
            throw new IllegalArgumentException("You are not authorized to add store owners.");
        }
        if (isUserStoreOwner(storeId, storeOwnerRequestDto.getUserId())) {
            throw new IllegalArgumentException("User is already a store owner.");
        }

        AppUser appUser = userService.getUserById(storeOwnerRequestDto.getUserId());
        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .appUser(appUser)
                .role(storeOwnerRequestDto.getRole())
                .build();
        storeOwner = storeOwnerRepository.save(storeOwner);

        store.getStoreOwners().add(storeOwner);
        return storeOwner;
    }

    @Override
    public void removeStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = getStoreById(storeId);
        if (!isUserStoreOwner(storeId, AuthUtil.getLoggedInUser().getId())) {
            throw new IllegalArgumentException("You are not authorized to remove store owners.");
        }
        if (!isUserStoreOwner(storeId, storeOwnerRequestDto.getUserId())) {
            throw new IllegalArgumentException("User requested for removal is not a store owner.");
        }

        StoreOwner storeOwner = storeOwnerRepository.findByAppUserId(storeOwnerRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Store owner not found."));
        // Prevent removing the CREATOR
        if (storeOwner.getRole() == CREATOR) {
            throw new IllegalArgumentException("The creator of the store cannot be removed.");
        }

        store.getStoreOwners().remove(storeOwner);
        storeOwnerRepository.delete(storeOwner);
    }

    private boolean isUserStoreOwner(int storeId, int userId) {
        AppUser appUser = userService.getUserById(userId);
        Store store = getStoreById(storeId);
        return store.getStoreOwners().stream()
                .anyMatch(it -> it.getAppUser().equals(appUser));
    }
}
