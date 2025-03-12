package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreOwnerResponseDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.dto.store.StoreResponseDto;
import com.mercheazy.server.entity.store.Store;
import com.mercheazy.server.entity.store.StoreOwner;
import com.mercheazy.server.entity.user.AppUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreOwnerRepository;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mercheazy.server.entity.store.StoreOwner.Role.CREATOR;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final StoreOwnerRepository storeOwnerRepository;

    @Override
    public StoreResponseDto addStore(StoreRequestDto storeRequestDto) {
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
        return storeRepository.save(store).toStoreResponseDto();
    }


    @Override
    public StoreResponseDto updateStoreDetails(int id, StoreRequestDto storeRequestDto) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        store.setName(storeRequestDto.getName());
        store.setDesc(storeRequestDto.getDesc());
        return storeRepository.save(store).toStoreResponseDto();
    }

    @Override
    public List<StoreResponseDto> getStores() {
        return storeRepository.findAll().stream().map(Store::toStoreResponseDto).toList();
    }

    @Override
    public StoreResponseDto getStoreById(int id) {
        return storeRepository.findById(id).map(Store::toStoreResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));
    }

    @Override
    public List<StoreOwnerResponseDto> getStoreOwnersByStoreId(int storeId) {
        return storeOwnerRepository.findByStoreId(storeId).stream().map(StoreOwner::toStoreOwnerResponseDto).toList();
    }

    @Override
    public StoreResponseDto getStoreByUserId(int userId) {
        return storeOwnerRepository.findByAppUserId(userId).map(StoreOwner::getStore).map(Store::toStoreResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("No store found for this user."));
    }

    @Override
    public void deleteStore(int id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreOwnerResponseDto addStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        boolean isUserAuthorized = store.getStoreOwners().stream()
                .anyMatch(it -> it.getAppUser().equals(AuthUtil.getLoggedInUser()) && it.getRole() == CREATOR);

        if (!isUserAuthorized) {
            throw new IllegalArgumentException("You are not authorized to add store owners.");
        }

        AppUser appUser = userRepository.findById(storeOwnerRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        boolean alreadyOwner = store.getStoreOwners().stream()
                .anyMatch(it -> it.getAppUser().equals(appUser));

        if (alreadyOwner) {
            throw new IllegalArgumentException("User is already a store owner.");
        }

        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .appUser(appUser)
                .role(storeOwnerRequestDto.getRole())
                .build();
        storeOwner = storeOwnerRepository.save(storeOwner);

        store.getStoreOwners().add(storeOwner);
        return storeOwner.toStoreOwnerResponseDto();
    }

    @Override
    public void removeStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        boolean isUserAuthorized = store.getStoreOwners().stream()
                .anyMatch(it -> it.getAppUser().equals(AuthUtil.getLoggedInUser()) && it.getRole() == CREATOR);

        if (!isUserAuthorized) {
            throw new IllegalArgumentException("You are not authorized to remove store owners.");
        }

        AppUser appUser = userRepository.findById(storeOwnerRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        StoreOwner storeOwner = store.getStoreOwners().stream()
                .filter(it -> it.getAppUser().equals(appUser))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User is not a store owner."));

        // Prevent removing the CREATOR
        if (storeOwner.getRole() == CREATOR) {
            throw new IllegalArgumentException("The creator of the store cannot be removed.");
        }

        store.getStoreOwners().remove(storeOwner);
        storeOwnerRepository.delete(storeOwner);
    }
}
