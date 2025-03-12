package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.Order;
import com.mercheazy.server.entity.Order.OrderStatus;
import com.mercheazy.server.entity.OrderItem;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.OrderRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements com.mercheazy.server.service.OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty.");
        }

        Order order = Order.builder()
                .user(AuthUtil.getLoggedInUser())
                .status(OrderStatus.PENDING)
                .build();
        order = orderRepository.save(order);

        List<OrderItem> orderItems = saveOrderItems(orderRequestDto.getOrderItems(), order);

        order.setOrderItems(orderItems);
        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order).toOrderResponseDto();
    }

    private List<OrderItem> saveOrderItems(List<OrderItemRequestDto> orderItemRequestDtos, Order order) {
        return orderItemRequestDtos.stream()
                .map(requestDtoOrderItem -> {
                    Product product = productRepository.findById(requestDtoOrderItem.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .price(product.getSellPrice())
                            .quantity(requestDtoOrderItem.getQuantity())
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return orderRepository.findByUserId(user.getId())
                .stream().map(Order::toOrderResponseDto).toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(int id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        order.setStatus(status);
        return orderRepository.save(order).toOrderResponseDto();
    }
}
