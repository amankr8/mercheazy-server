package com.mercheazy.server.service.impl;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.service.StoreService;

import java.util.List;

public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;

    @Override
    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(int id, Store newStore) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store does not exist."));

        store.setName(newStore.getName());
        store.setDesc(newStore.getDesc());
        return store;
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
        return storeRepository.findByUser(user);
    }

    @Override
    public void deleteStore(int id) {
        storeRepository.deleteById(id);
    }
}
