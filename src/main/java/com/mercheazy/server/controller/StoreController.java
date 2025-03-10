package com.mercheazy.server.controller;

import com.mercheazy.server.dto.store.StoreOwnerRequestDto;
import com.mercheazy.server.dto.store.StoreRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/store")
public interface StoreController {

    @PostMapping
    ResponseEntity<?> createStore(@RequestBody StoreRequestDto storeRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<?> updateStore(@PathVariable int id, @RequestBody StoreRequestDto storeRequestDto);

    @GetMapping
    ResponseEntity<?> getStores();

    @GetMapping("/user")
    ResponseEntity<?> getUserStore();

    @GetMapping("/{id}")
    ResponseEntity<?> getStoreById(@PathVariable int id);

    @GetMapping("/{storeId}/owners")
    ResponseEntity<?> getStoreOwnersByStoreId(@PathVariable int storeId);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStoreById(@PathVariable int id);

    @PostMapping("/add-owner")
    ResponseEntity<?> createStoreOwner(@RequestBody StoreOwnerRequestDto storeOwnerRequestDto);

    @DeleteMapping("/remove-owner")
    ResponseEntity<?> removeStoreOwner(@RequestBody StoreOwnerRequestDto storeOwnerRequestDto);
}
