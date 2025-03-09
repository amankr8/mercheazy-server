package com.mercheazy.server.service;

import com.mercheazy.server.dto.StoreOwnerRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;

public interface StoreOwnerService {
    StoreOwner addStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto);

    void saveStoreOwner(Store store);

    StoreOwner getStoreOwnerByUser(User user);

    void deleteStoreOwner(int id);
}
