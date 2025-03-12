package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.StoreController;
import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;

    @Override
    public ResponseEntity<?> createStore(StoreRequestDto storeRequestDto) {
        return ResponseEntity.ok(storeService.addStore(storeRequestDto));
    }

    @Override
    public ResponseEntity<?> updateStore(int id, StoreRequestDto storeRequestDto) {
        return ResponseEntity.ok(storeService.updateStoreDetails(id, storeRequestDto));
    }

    @Override
    public ResponseEntity<?> getStores() {
        return ResponseEntity.ok(storeService.getStores());
    }

    @Override
    public ResponseEntity<?> getUserStore() {
        return ResponseEntity.ok(storeService.getStoreByUserId(AuthUtil.getLoggedInUser().getId()));
    }

    @Override
    public ResponseEntity<?> getStoreById(int id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @Override
    public ResponseEntity<?> getStoreOwnersByStoreId(int storeId) {
        return ResponseEntity.ok(storeService.getStoreOwnersByStoreId(storeId));
    }

    @Override
    public ResponseEntity<?> deleteStoreById(int id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok("Store deleted successfully!");
    }

    @Override
    public ResponseEntity<?> createStoreOwner(int id, StoreOwnerRequestDto storeOwnerRequestDto) {
        return ResponseEntity.ok(storeService.addStoreOwner(id, storeOwnerRequestDto));
    }

    @Override
    public ResponseEntity<?> removeStoreOwner(int id, StoreOwnerRequestDto storeOwnerRequestDto) {
        storeService.removeStoreOwner(id, storeOwnerRequestDto);
        return ResponseEntity.ok("Store owner removed successfully!");
    }
}
