package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.StoreController;
import com.mercheazy.server.dto.StoreOwnerRequestDto;
import com.mercheazy.server.dto.StoreRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.service.StoreOwnerService;
import com.mercheazy.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;
    private final StoreOwnerService storeOwnerService;

    @Override
    public ResponseEntity<?> createStore(StoreRequestDto storeRequestDto) {
        return ResponseEntity.ok(storeService.addStore(storeRequestDto));
    }

    @Override
    public ResponseEntity<?> updateStore(int id,Store store) {
        return ResponseEntity.ok(storeService.updateStore(id, store));
    }

    @Override
    public ResponseEntity<?> getStores() {
        return ResponseEntity.ok(storeService.getStores());
    }

    @Override
    public ResponseEntity<?> getStoreById(int id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @Override
    public ResponseEntity<?> deleteStoreById(int id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok("Store deleted successfully!");
    }

    @Override
    public ResponseEntity<?> createStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto) {
        return ResponseEntity.ok(storeOwnerService.addStoreOwner(storeOwnerRequestDto));
    }

    @Override
    public ResponseEntity<?> deleteStoreOwner(int id) {
        storeOwnerService.deleteStoreOwner(id);
        return ResponseEntity.ok("Store owner deleted successfully!");
    }
}
