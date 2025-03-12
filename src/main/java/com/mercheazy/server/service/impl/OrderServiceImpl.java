package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.cart.Cart;
import com.mercheazy.server.entity.cart.CartItem;
import com.mercheazy.server.entity.order.MerchOrder;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;
import com.mercheazy.server.entity.order.MerchOrderItem;
import com.mercheazy.server.entity.product.Product;
import com.mercheazy.server.entity.user.AppUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CartRepository;
import com.mercheazy.server.repository.OrderRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.CartService;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements com.mercheazy.server.service.OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;

    @Override
    public OrderResponseDto placeOrder(OrderItemRequestDto orderItemRequestDto) {
        Product product = productService.getProductById(orderItemRequestDto.getProductId());
        if (productService.outOfStock(product.getId(), orderItemRequestDto.getQuantity())) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock.");
        }

        MerchOrder merchOrder = MerchOrder.builder()
                .appUser(AuthUtil.getLoggedInUser())
                .merchOrderItems(new ArrayList<>())
                .totalPrice(0.0)
                .status(OrderStatus.PENDING)
                .build();
        merchOrder = orderRepository.save(merchOrder);

        MerchOrderItem merchOrderItem = MerchOrderItem.builder()
                        .product(product)
                        .merchOrder(merchOrder)
                        .quantity(orderItemRequestDto.getQuantity())
                        .price(product.getSellPrice())
                        .build();

        merchOrder.getMerchOrderItems().add(merchOrderItem);
        double totalPrice = merchOrderItem.getPrice() * merchOrderItem.getQuantity();
        merchOrder.setTotalPrice(totalPrice);
        merchOrder = orderRepository.save(merchOrder);

        productService.updateStock(product.getId(), product.getStock() - orderItemRequestDto.getQuantity());
        return merchOrder.toOrderResponseDto();
    }

    @Override
    public OrderResponseDto checkoutCartByUserId(int userId) {
        AppUser appUser = userService.getUserById(userId);
        Cart cart = cartRepository.findByAppUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found."));
        if (cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty.");
        }

        MerchOrder merchOrder = MerchOrder.builder()
                .appUser(appUser)
                .status(OrderStatus.PENDING)
                .build();
        merchOrder = orderRepository.save(merchOrder);

        List<MerchOrderItem> merchOrderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = productService.getProductById(cartItem.getProduct().getId());
            if (productService.outOfStock(product.getId(), cartItem.getQuantity())) {
                throw new IllegalArgumentException("Requested quantity exceeds available stock.");
            }

            MerchOrderItem merchOrderItem = MerchOrderItem.builder()
                    .merchOrder(merchOrder)
                    .product(product)
                    .price(product.getSellPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            merchOrderItems.add(merchOrderItem);
            productService.updateStock(product.getId(), product.getStock() - cartItem.getQuantity());
        }

        merchOrder.setMerchOrderItems(merchOrderItems);
        double totalPrice = merchOrderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        merchOrder.setTotalPrice(totalPrice);
        merchOrder = orderRepository.save(merchOrder);

        // Clear user cart
        cartService.clearCartByUserId(userId);
        return merchOrder.toOrderResponseDto();
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(int userId) {
        AppUser appUser = userService.getUserById(userId);
        return orderRepository.findByAppUserId(appUser.getId())
                .stream().map(MerchOrder::toOrderResponseDto).toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(int id, OrderStatus status) {
        MerchOrder merchOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        merchOrder.setStatus(status);
        return orderRepository.save(merchOrder).toOrderResponseDto();
    }
}
