package com.mercheazy.server.service;

import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.User;

public interface CartService {
    void createUserCart(User user);

    Cart getUserCart(User user);

    void deleteUserCart(User user);
}
