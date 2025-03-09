package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.CartItemRequestDto;
import com.mercheazy.server.dto.CartItemResponseDto;
import com.mercheazy.server.dto.CartResponseDto;
import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.entity.Cart;
import com.mercheazy.server.entity.CartItem;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CartItemRepository;
import com.mercheazy.server.repository.CartRepository;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements com.mercheazy.server.service.CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Override
    public void createUserCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    @Override
    public CartItemResponseDto addToCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = getUserCartByUser(AuthUtil.getLoggedInUser());
        Product product = productService.getProductById(cartItemRequestDto.getProductId());
        List<FileResponseDto> images = productService.getImagesByProduct(product);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(cartItemRequestDto.getQuantity())
                .build();

        return cartItemRepository.save(cartItem).toCartItemResponseDto(images);
    }

    @Override
    public void updateCartItem(int id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart."));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public Cart getUserCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
    }

    @Override
    public CartResponseDto getUserCart() {
        Cart cart = getUserCartByUser(AuthUtil.getLoggedInUser());
        List<CartItemResponseDto> cartItems = cartItemRepository.findByCart(cart).stream().map(cartItem -> {
            List<FileResponseDto> images = productService.getImagesByProduct(cartItem.getProduct());
            return cartItem.toCartItemResponseDto(images);
        }).toList();

        return CartResponseDto.builder()
                .id(cart.getId())
                .cartItems(cartItems)
                .build();
    }

    @Override
    public void deleteUserCart(User user) {
        cartRepository.findByUser(user).ifPresent(cartRepository::delete);
    }

    @Override
    public void removeFromCart(int id) {
        cartItemRepository.deleteById(id);
    }
}
