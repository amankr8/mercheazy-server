package com.mercheazy.server.service;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.entity.cart.Cart;
import com.mercheazy.server.entity.user.AuthUser;

public interface CartService {
    void createUserCart(AuthUser authUser);

    Cart addToCart(CartItemRequestDto cartRequestDto, int userId);

    Cart removeFromCart(CartItemRequestDto cartItemRequestDto, int userId);

    Cart getCartByUserId(int userId);

    void clearCartById(int id);
}
