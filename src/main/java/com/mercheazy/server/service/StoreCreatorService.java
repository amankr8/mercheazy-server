package com.mercheazy.server.service;

import com.mercheazy.server.dto.StoreCreatorRequestDto;
import com.mercheazy.server.entity.StoreCreator;

public interface StoreCreatorService {
    StoreCreator addStoreCreator(StoreCreatorRequestDto storeCreatorRequestDto);
}
