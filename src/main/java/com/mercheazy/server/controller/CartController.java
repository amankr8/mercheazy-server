package com.mercheazy.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/cart")
public interface CartController {
    @PostMapping
    ResponseEntity<?> addToCart();

    @PutMapping
    ResponseEntity<?> updateCart();

    @DeleteMapping
    ResponseEntity<?> removeFromCart();
}
