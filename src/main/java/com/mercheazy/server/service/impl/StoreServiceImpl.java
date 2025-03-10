package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.dto.store.StoreResponseDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mercheazy.server.entity.StoreOwner.Role.CREATOR;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public StoreResponseDto addStore(StoreRequestDto storeRequestDto) {
        Store store = Store.builder()
                .name(storeRequestDto.getName())
                .desc(storeRequestDto.getDesc())
                .storeOwners(new ArrayList<>())
                .build();

        store = storeRepository.save(store);

        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .user(AuthUtil.getLoggedInUser())
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
    public void deleteStore(int id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreResponseDto addStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = storeRepository.findById(storeOwnerRequestDto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        User loggedInUser = AuthUtil.getLoggedInUser();
        boolean isUserAuthorized = store.getStoreOwners().stream()
                .anyMatch(it -> it.getUser().equals(loggedInUser) && it.getRole() == CREATOR);

        if (!isUserAuthorized) {
            throw new IllegalArgumentException("You are not authorized to add store owners.");
        }

        User user = userRepository.findById(storeOwnerRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        boolean alreadyOwner = store.getStoreOwners().stream()
                .anyMatch(it -> it.getUser().equals(user));

        if (alreadyOwner) {
            throw new IllegalArgumentException("User is already a store owner.");
        }

        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .user(user)
                .role(storeOwnerRequestDto.getRole())
                .build();

        store.getStoreOwners().add(storeOwner);
        return storeRepository.save(store).toStoreResponseDto();
    }

    @Override
    public StoreResponseDto removeStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto) {
        Store store = storeRepository.findById(storeOwnerRequestDto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        User loggedInUser = AuthUtil.getLoggedInUser();
        boolean isUserAuthorized = store.getStoreOwners().stream()
                .anyMatch(it -> it.getUser().equals(loggedInUser) && it.getRole() == CREATOR);

        if (!isUserAuthorized) {
            throw new IllegalArgumentException("You are not authorized to remove store owners.");
        }

        User user = userRepository.findById(storeOwnerRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        StoreOwner storeOwner = store.getStoreOwners().stream()
                .filter(it -> it.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User is not a store owner."));

        // Prevent removing the CREATOR
        if (storeOwner.getRole() == CREATOR) {
            throw new IllegalArgumentException("The creator of the store cannot be removed.");
        }

        store.getStoreOwners().remove(storeOwner);
        return storeRepository.save(store).toStoreResponseDto();
    }

}
