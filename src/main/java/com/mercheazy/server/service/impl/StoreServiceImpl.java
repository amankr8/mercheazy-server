package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.StoreRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreCreatorRepository;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreCreatorRepository storeCreatorRepository;

    @Override
    public Store addStore(StoreRequestDto storeRequestDto) {
        Store store = storeRepository.save(storeRequestDto.toStore());
        StoreCreator storeCreator = StoreCreator.builder()
                .store(store)
                .user(AuthUtil.getLoggedInUser())
                .role(StoreCreator.Role.CREATOR)
                .build();
        storeCreatorRepository.save(storeCreator);

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
        return null;
    }

    @Override
    public void deleteStore(int id) {
        storeRepository.deleteById(id);
    }
}
