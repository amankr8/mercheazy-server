package com.mercheazy.server.controller;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
public interface CartController {
    @PostMapping("/add-to-cart")
    ResponseEntity<?> addToCart(@RequestBody CartItemRequestDto cartItemRequestDto);

    @DeleteMapping("/remove-from-cart")
    ResponseEntity<?> removeFromCart(@RequestBody CartItemRequestDto cartItemRequestDto);
}
