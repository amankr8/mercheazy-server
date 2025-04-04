package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.entity.cart.Cart;
import com.mercheazy.server.entity.cart.CartItem;
import com.mercheazy.server.entity.product.Product;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.cart.CartItemRepository;
import com.mercheazy.server.repository.cart.CartRepository;
import com.mercheazy.server.repository.product.ProductRepository;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements com.mercheazy.server.service.CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public Cart createUserCart(int userId) {
        AuthUser user = userService.getUserById(userId);
        Cart cart = Cart.builder()
                .authUser(user)
                .cartItems(new ArrayList<>())
                .build();
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(int userId) {
        return cartRepository.findByAuthUserId(userId).orElseGet(() -> createUserCart(userId));
    }

    @Override
    public Cart addToCart(CartItemRequestDto cartItemRequestDto, int userId) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.getProductById(cartItemRequestDto.getProductId());

        CartItem cartItem = cart.getCartItems().stream()
                .filter(it -> it.getProduct().equals(product))
                .findFirst().orElse(null);

        if (cartItem == null) {
            int requestQuantity = cartItemRequestDto.getQuantity();
            if (requestQuantity > product.getStock()) {
                throw new IllegalArgumentException("Requested quantity exceeds available stock.");
            }
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(cartItemRequestDto.getQuantity())
                    .build();
            cart.getCartItems().add(cartItem);
        } else {
            int requestQuantity = cartItem.getQuantity() + cartItemRequestDto.getQuantity();
            if (requestQuantity > product.getStock()) {
                throw new IllegalArgumentException("Requested quantity exceeds available stock.");
            }
            cartItem.setQuantity(requestQuantity);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeFromCart(CartItemRequestDto cartItemRequestDto, int userId) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.getProductById(cartItemRequestDto.getProductId());

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

        return cartRepository.save(cart);
    }

    @Override
    public void clearCartById(int id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
