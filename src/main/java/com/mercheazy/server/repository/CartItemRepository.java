package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.CartItem;
import com.mercheazy.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    List<CartItem> findByCart(Cart cart);
}
