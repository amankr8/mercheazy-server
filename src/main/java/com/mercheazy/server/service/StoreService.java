package com.mercheazy.server.service;

import com.mercheazy.server.dto.StoreRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.User;

import java.util.List;

public interface StoreService {

    Store addStore(StoreRequestDto storeRequestDto);

    Store updateStore(int id, Store newStore);

    List<Store> getStores();

    Store getStoreById(int id);

    Store getStoreByUser(User user);

    void deleteStore(int id);
}
