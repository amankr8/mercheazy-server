package com.mercheazy.server.service;

import com.mercheazy.server.dto.StoreCreatorRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;

public interface StoreCreatorService {
    StoreCreator addStoreCreator(StoreCreatorRequestDto storeCreatorRequestDto);

    void saveStoreCreator(Store store);

    StoreCreator getStoreCreatorByUser(User user);
}
