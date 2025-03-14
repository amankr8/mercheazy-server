package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.StoreController;
import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreOwnerResponseDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import com.mercheazy.server.dto.store.StoreResponseDto;
import com.mercheazy.server.entity.store.Store;
import com.mercheazy.server.entity.store.StoreOwner;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StoreControllerImpl implements StoreController {
    private final StoreService storeService;

    @Override
    public ResponseEntity<?> createStore(StoreRequestDto storeRequestDto) {
        StoreResponseDto store = storeService.addStore(storeRequestDto).toStoreResponseDto();
        return ResponseEntity.ok(store);
    }

    @Override
    public ResponseEntity<?> updateStore(int id, StoreRequestDto storeRequestDto) {
        StoreResponseDto updatedStore = storeService.updateStoreDetails(id, storeRequestDto).toStoreResponseDto();
        return ResponseEntity.ok(updatedStore);
    }

    @Override
    public ResponseEntity<?> getAllStores() {
        List<StoreResponseDto> stores = storeService.getAllStores().stream().map(Store::toStoreResponseDto).toList();
        return ResponseEntity.ok(stores);
    }

    @Override
    public ResponseEntity<?> getUserStore() {
        AuthUser loggedInUser = AuthUtil.getLoggedInUser();
        StoreResponseDto userStore = storeService.getStoreByUserId(loggedInUser.getId()).toStoreResponseDto();
        return ResponseEntity.ok(userStore);
    }

    @Override
    public ResponseEntity<?> getStoreById(int id) {
        StoreResponseDto store = storeService.getStoreById(id).toStoreResponseDto();
        return ResponseEntity.ok(store);
    }

    @Override
    public ResponseEntity<?> getStoreOwnersByStoreId(int storeId) {
        List<StoreOwnerResponseDto> storeOwners = storeService.getStoreOwnersByStoreId(storeId).stream()
                .map(StoreOwner::toStoreOwnerResponseDto).toList();
        return ResponseEntity.ok(storeOwners);
    }

    @Override
    public ResponseEntity<?> deleteStoreById(int id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok("Store deleted successfully!");
    }

    @Override
    public ResponseEntity<?> createStoreOwner(int id, StoreOwnerRequestDto storeOwnerRequestDto) {
        StoreOwnerResponseDto storeOwner = storeService.addStoreOwner(id, storeOwnerRequestDto).toStoreOwnerResponseDto();
        return ResponseEntity.ok(storeOwner);
    }

    @Override
    public ResponseEntity<?> removeStoreOwner(int id, StoreOwnerRequestDto storeOwnerRequestDto) {
        storeService.removeStoreOwner(id, storeOwnerRequestDto);
        return ResponseEntity.ok("Store owner removed successfully!");
    }
}
