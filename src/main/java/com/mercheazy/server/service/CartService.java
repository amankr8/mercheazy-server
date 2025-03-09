package com.mercheazy.server.service;

import com.mercheazy.server.dto.CartItemRequestDto;
import com.mercheazy.server.dto.CartItemResponseDto;
import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.User;

public interface CartService {
    void createUserCart(User user);

    CartItemResponseDto addToCart(CartItemRequestDto cartRequestDto);

    Cart getUserCart(User user);

    void deleteUserCart(User user);
}
