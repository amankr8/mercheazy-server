package com.mercheazy.server.controller;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/stores")
public interface StoreController {

    @PostMapping
    ResponseEntity<?> createStore(@RequestBody StoreRequestDto storeRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<?> updateStore(@PathVariable int id, @RequestBody StoreRequestDto storeRequestDto);

    @GetMapping
    ResponseEntity<?> getAllStores();

    @GetMapping("/user")
    ResponseEntity<?> getUserStore();

    @GetMapping("/{id}")
    ResponseEntity<?> getStoreById(@PathVariable int id);

    @GetMapping("/{storeId}/owners")
    ResponseEntity<?> getStoreOwnersByStoreId(@PathVariable int storeId);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStoreById(@PathVariable int id);

    @PostMapping("/{id}/add-owner")
    ResponseEntity<?> createStoreOwner(@PathVariable int id, @RequestBody StoreOwnerRequestDto storeOwnerRequestDto);

    @DeleteMapping("/{id}/remove-owner")
    ResponseEntity<?> removeStoreOwner(@PathVariable int id, @RequestBody StoreOwnerRequestDto storeOwnerRequestDto);
}
