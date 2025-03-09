package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.StoreOwnerRequestDto;
import com.mercheazy.server.dto.StoreRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreOwnerRepository;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mercheazy.server.entity.StoreOwner.Role.CREATOR;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreOwnerRepository storeOwnerRepository;

    @Override
    public Store addStore(StoreRequestDto storeRequestDto) {
        Store store = storeRepository.save(storeRequestDto.toStore());
        saveStoreOwner(store);
        return store;
    }

    @Override
    public Store updateStore(int id, Store newStore) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        store.setName(newStore.getName());
        store.setDesc(newStore.getDesc());
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(int id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public Store getStoreByUser(User user) {
        return getStoreOwnerByUser(user).getStore();
    }

    @Override
    public void deleteStore(int id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreOwner addStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto) {
        User user = AuthUtil.getLoggedInUser();
        StoreOwner storeOwner = getStoreOwnerByUser(user);
        Store store = storeOwner.getStore();
        if (store.getId() != storeOwnerRequestDto.getStoreId() || storeOwner.getRole() != CREATOR) {
            throw new IllegalArgumentException("User is unauthorized to add a store owner to this store.");
        }
        return storeOwnerRepository.save(storeOwnerRequestDto.toStoreCreator());
    }

    @Override
    public void saveStoreOwner(Store store) {
        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .user(AuthUtil.getLoggedInUser())
                .role(CREATOR)
                .build();
        storeOwnerRepository.save(storeOwner);
    }

    @Override
    public StoreOwner getStoreOwnerByUser(User user) {
        return storeOwnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not have a store."));
    }

    @Override
    public void deleteStoreOwner(int id) {
        storeOwnerRepository.deleteById(id);
    }
}
