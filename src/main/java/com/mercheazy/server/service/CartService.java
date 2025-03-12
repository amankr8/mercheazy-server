package com.mercheazy.server.service;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.dto.cart.CartResponseDto;
import com.mercheazy.server.entity.user.AppUser;

public interface CartService {
    void createUserCart(AppUser appUser);

    CartResponseDto addToCart(CartItemRequestDto cartRequestDto);

    CartResponseDto removeFromCart(CartItemRequestDto cartItemRequestDto);

    CartResponseDto getCartByUserId(int userId);
}
