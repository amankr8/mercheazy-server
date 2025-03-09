package com.mercheazy.server.controller.impl;

import com.mercheazy.server.dto.CartItemRequestDto;
import com.mercheazy.server.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements com.mercheazy.server.controller.CartController {
    private final CartService cartService;

    @Override
    public ResponseEntity<?> addToCart(CartItemRequestDto cartItemRequestDto) {
        return ResponseEntity.ok(cartService.addToCart(cartItemRequestDto));
    }

    @Override
    public ResponseEntity<?> updateCart() {
        return null;
    }

    @Override
    public ResponseEntity<?> removeFromCart(int id) {
        return null;
    }
}
