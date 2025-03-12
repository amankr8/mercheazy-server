package com.mercheazy.server.repository;

import com.mercheazy.server.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByAppUserId(int userId);
}
