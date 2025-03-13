package com.mercheazy.server.controller.impl;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.dto.cart.CartResponseDto;
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
        CartResponseDto userCart = cartService.getCartByUserId(AuthUtil.getLoggedInUser().getId()).toCartResponseDto();
        return ResponseEntity.ok(userCart);
    }

    @Override
    public ResponseEntity<?> addToCart(CartItemRequestDto cartItemRequestDto) {
        CartResponseDto cart = cartService.addToCart(cartItemRequestDto).toCartResponseDto();
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<?> removeFromCart(CartItemRequestDto cartItemRequestDto) {
        CartResponseDto cart = cartService.removeFromCart(cartItemRequestDto).toCartResponseDto();
        return ResponseEntity.ok(cart);
    }
}
