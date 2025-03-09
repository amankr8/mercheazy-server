package com.mercheazy.server.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements com.mercheazy.server.controller.CartController {

    @Override
    public ResponseEntity<?> addToCart() {
        return null;
    }

    @Override
    public ResponseEntity<?> updateCart() {
        return null;
    }

    @Override
    public ResponseEntity<?> removeFromCart() {
        return null;
    }
}
