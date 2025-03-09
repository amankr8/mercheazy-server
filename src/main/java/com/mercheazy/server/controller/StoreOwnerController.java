package com.mercheazy.server.controller;

import com.mercheazy.server.dto.StoreOwnerRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/store-owner")
public interface StoreOwnerController {
    @PostMapping
    ResponseEntity<?> createStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto);

    @DeleteMapping
    ResponseEntity<?> deleteStoreOwner(int id);
}
