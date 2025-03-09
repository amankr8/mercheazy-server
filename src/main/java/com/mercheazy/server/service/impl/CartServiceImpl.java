package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.CartItemRequestDto;
import com.mercheazy.server.dto.CartItemResponseDto;
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
        Cart cart = getUserCart(AuthUtil.getLoggedInUser());
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
    public Cart getUserCart(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("User cart not found."));
    }

    @Override
    public void deleteUserCart(User user) {
        cartRepository.findByUser(user).ifPresent(cartRepository::delete);
    }
}
