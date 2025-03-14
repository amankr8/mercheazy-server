package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.cart.CartItemRequestDto;
import com.mercheazy.server.entity.cart.Cart;
import com.mercheazy.server.entity.cart.CartItem;
import com.mercheazy.server.entity.product.Product;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CartItemRepository;
import com.mercheazy.server.repository.CartRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements com.mercheazy.server.service.CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    public void createUserCart(AuthUser authUser) {
        Cart cart = Cart.builder()
                .authUser(authUser)
                .cartItems(new ArrayList<>())
                .build();
        cartRepository.save(cart);
        System.out.println("User cart created.");
    }

    @Override
    public Cart getCartByUserId(int userId) {
        return cartRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
    }

    @Override
    public Cart addToCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartRepository.findByAuthUserId(AuthUtil.getLoggedInUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
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
    public Cart removeFromCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartRepository.findByAuthUserId(AuthUtil.getLoggedInUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
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
    public void clearCartByUserId(int userId) {
        Cart cart = cartRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
