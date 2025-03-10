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

    @GetMapping("/{id}")
    ResponseEntity<?> getStoreById(@PathVariable int id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStoreById(@PathVariable int id);

    @PostMapping("/owner")
    ResponseEntity<?> createStoreOwner(@RequestBody StoreOwnerRequestDto storeOwnerRequestDto);

    @DeleteMapping("/owner")
    ResponseEntity<?> deleteStoreOwner(@RequestBody StoreOwnerRequestDto storeOwnerRequestDto);
}
