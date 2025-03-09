package com.mercheazy.server.controller;

import com.mercheazy.server.dto.CartItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
public interface CartController {
    @PostMapping
    ResponseEntity<?> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto);

    @PutMapping
    ResponseEntity<?> updateCart();

    @DeleteMapping("/{id}")
    ResponseEntity<?> removeFromCart(@PathVariable int id);
}
