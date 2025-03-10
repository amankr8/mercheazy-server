package com.mercheazy.server.service;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.dto.store.StoreResponseDto;

import java.util.List;

public interface StoreService {

    StoreResponseDto addStore(StoreRequestDto storeRequestDto);

    StoreResponseDto updateStoreDetails(int id, StoreRequestDto storeRequestDto);

    List<StoreResponseDto> getStores();

    StoreResponseDto getStoreById(int id);

    void deleteStore(int id);

    StoreResponseDto addStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto);

    StoreResponseDto removeStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto);
}
