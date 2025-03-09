package com.mercheazy.server.controller;

import com.mercheazy.server.dto.StoreOwnerRequestDto;
import com.mercheazy.server.dto.StoreRequestDto;
import com.mercheazy.server.entity.Store;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/store")
public interface StoreController {

    @PostMapping
    ResponseEntity<?> createStore(@RequestBody StoreRequestDto storeRequestDto);

    @PutMapping
    ResponseEntity<?> updateStore(@PathVariable int id, @RequestBody Store store);

    @GetMapping
    ResponseEntity<?> getStores();

    @GetMapping("/{id}")
    ResponseEntity<?> getStoreById(@PathVariable int id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStoreById(@PathVariable int id);

    @PostMapping("/add-owner")
    ResponseEntity<?> createStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto);

    @DeleteMapping("/remove-owner")
    ResponseEntity<?> deleteStoreOwner(int id);
}
