package com.mercheazy.server.service;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.dto.cart.CartResponseDto;
import com.mercheazy.server.entity.cart.Cart;
import com.mercheazy.server.entity.user.AppUser;

public interface CartService {
    void createUserCart(AppUser appUser);

    Cart addToCart(CartItemRequestDto cartRequestDto);

    Cart removeFromCart(CartItemRequestDto cartItemRequestDto);

    Cart getCartByUserId(int userId);

    void clearCartByUserId(int userId);
}
