package com.mercheazy.server.service;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.entity.store.Store;
import com.mercheazy.server.entity.store.StoreOwner;

import java.util.List;

public interface StoreService {

    Store addStore(StoreRequestDto storeRequestDto);

    Store updateStoreDetails(int id, StoreRequestDto storeRequestDto);

    List<Store> getAllStores();

    Store getStoreById(int id);

    List<StoreOwner> getStoreOwnersByStoreId(int storeId);

    StoreOwner getStoreOwnerByStoreIdAndUserId(int storeId, int userId);

    Store getStoreByUserId(int userId);

    void deleteStore(int id);

    StoreOwner addStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto);

    void removeStoreOwner(int storeId, StoreOwnerRequestDto storeOwnerRequestDto);
}
