package com.mercheazy.server.controller;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
public interface CartController {
    @GetMapping("/user")
    ResponseEntity<?> getUserCart();

    @PostMapping
    ResponseEntity<?> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto);

    @DeleteMapping
    ResponseEntity<?> removeFromCart(@RequestBody CartItemRequestDto cartItemRequestDto);
}
