package com.mercheazy.server.controller.impl;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.service.CartService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class CartControllerImpl implements com.mercheazy.server.controller.CartController {
    private final CartService cartService;

    @Override
    public ResponseEntity<?> getUserCart() {
        return ResponseEntity.ok(cartService.getCartByUserId(AuthUtil.getLoggedInUserId()));
    }

    @Override
    public ResponseEntity<?> addToCart(CartItemRequestDto cartItemRequestDto) {
        return ResponseEntity.ok(cartService.addToCart(cartItemRequestDto));
    }

    @Override
    public ResponseEntity<?> removeFromCart(CartItemRequestDto cartItemRequestDto) {
        return ResponseEntity.ok(cartService.removeFromCart(cartItemRequestDto));
    }
}
