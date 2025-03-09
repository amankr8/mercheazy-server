package com.mercheazy.server.controller;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
public interface CartController {
    @PostMapping
    ResponseEntity<?> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<?> updateCartItem(@PathVariable int id, int quantity);

    @DeleteMapping("/{productId}")
    ResponseEntity<?> removeFromCart(@PathVariable int productId);
}
