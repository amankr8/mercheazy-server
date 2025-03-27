package com.mercheazy.server.service;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.entity.cart.Cart;

public interface CartService {
    Cart createUserCart(int userId);

    Cart addToCart(CartItemRequestDto cartRequestDto, int userId);

    Cart removeFromCart(CartItemRequestDto cartItemRequestDto, int userId);

    Cart getCartByUserId(int userId);

    void clearCartById(int id);
}
