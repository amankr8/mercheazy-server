package com.mercheazy.server.service.impl;

import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements com.mercheazy.server.service.CartService {
    private final CartRepository cartRepository;

    @Override
    public void createUserCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    @Override
    public Cart getUserCart(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
    }

    @Override
    public void deleteUserCart(User user) {
        cartRepository.findByUser(user).ifPresent(cartRepository::delete);
    }
}
