package com.mercheazy.server.service;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.dto.cart.CartResponseDto;
import com.mercheazy.server.entity.User;

public interface CartService {
    void createUserCart(User user);

    CartResponseDto addToCart(CartItemRequestDto cartRequestDto);

    CartResponseDto removeFromCart(CartItemRequestDto cartItemRequestDto);

    CartResponseDto getCartByUser(User user);

    void deleteUserCart(User user);
}
