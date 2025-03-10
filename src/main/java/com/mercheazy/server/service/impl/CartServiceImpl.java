package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.dto.cart.CartResponseDto;
import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.CartItem;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CartItemRepository;
import com.mercheazy.server.repository.CartRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements com.mercheazy.server.service.CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public void createUserCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        System.out.println("User cart created.");
    }

    @Override
    public CartResponseDto getUserCart() {
        return cartRepository.findByUser(AuthUtil.getLoggedInUser()).map(Cart::toCartResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
    }

    @Override
    public void deleteUserCart(User user) {
        cartRepository.findByUser(user).ifPresent(cartRepository::delete);
    }

    @Override
    public CartResponseDto addToCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartRepository.findByUser(AuthUtil.getLoggedInUser())
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
        Product product = productRepository.findById(cartItemRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(it -> it.getProduct().equals(product))
                .findFirst().orElse(null);

        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(cartItemRequestDto.getQuantity())
                    .build();
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequestDto.getQuantity());
        }

        return cartRepository.save(cart).toCartResponseDto();
    }

    @Override
    public CartResponseDto removeFromCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartRepository.findByUser(AuthUtil.getLoggedInUser())
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
        Product product = productRepository.findById(cartItemRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(it -> it.getProduct().equals(product))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in user's cart."));

        if (cartItem.getQuantity() > cartItemRequestDto.getQuantity()) {
            cartItem.setQuantity(cartItem.getQuantity() - cartItemRequestDto.getQuantity());
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        return cartRepository.save(cart).toCartResponseDto();
    }
}
