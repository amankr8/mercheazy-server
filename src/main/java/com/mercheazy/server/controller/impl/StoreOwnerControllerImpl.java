package com.mercheazy.server.controller.impl;

import com.mercheazy.server.dto.StoreOwnerRequestDto;
import com.mercheazy.server.service.StoreOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreOwnerControllerImpl implements com.mercheazy.server.controller.StoreOwnerController {
    private final StoreOwnerService storeOwnerService;

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
