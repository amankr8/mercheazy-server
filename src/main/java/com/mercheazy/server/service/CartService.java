package com.mercheazy.server.service;

import com.mercheazy.server.dto.CartItemRequestDto;
import com.mercheazy.server.dto.CartItemResponseDto;
import com.mercheazy.server.dto.CartResponseDto;
import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.User;

public interface CartService {
    void createUserCart(User user);

    CartItemResponseDto addToCart(CartItemRequestDto cartRequestDto);

    void updateCartItem(int id, int quantity);

    Cart getUserCartByUser(User user);

    CartResponseDto getUserCart();

    void deleteUserCart(User user);

    void removeFromCart(int id);
}
