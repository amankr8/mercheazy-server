package com.mercheazy.server.repository;

import com.mercheazy.server.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
