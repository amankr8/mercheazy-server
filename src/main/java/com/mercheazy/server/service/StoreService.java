package com.mercheazy.server.service;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;

import java.util.List;

public interface StoreService {

    Store addStore(StoreRequestDto storeRequestDto);

    Store updateStore(int id, Store newStore);

    List<Store> getStores();

    Store getStoreById(int id);

    Store getStoreByUser(User user);

    void deleteStore(int id);

    StoreOwner addStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto);

    void saveStoreOwner(Store store);

    StoreOwner getStoreOwnerByUser(User user);

    void deleteStoreOwner(int id);
}
