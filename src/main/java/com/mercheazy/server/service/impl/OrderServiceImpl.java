package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.Order;
import com.mercheazy.server.entity.Order.OrderStatus;
import com.mercheazy.server.entity.OrderItem;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.OrderRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements com.mercheazy.server.service.OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        List<OrderItemRequestDto> requestDtoOrderItems = orderRequestDto.getOrderItems();
        List<OrderItem> orderItems = requestDtoOrderItems.stream()
                .map(requestDtoOrderItem -> {
                    Product product = productRepository.findById(requestDtoOrderItem.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
                    return OrderItem.builder()
                            .product(product)
                            .price(product.getSellPrice())
                            .quantity(requestDtoOrderItem.getQuantity())
                            .build();
                }).toList();

        Order order = Order.builder()
                .store(storeRepository.findById(orderRequestDto.getStoreId())
                        .orElseThrow(() -> new ResourceNotFoundException("Store not found.")))
                .totalPrice(orderRequestDto.getTotalPrice())
                .user(AuthUtil.getLoggedInUser())
                .status(OrderStatus.PENDING)
                .orderItems(orderItems)
                .build();

        return orderRepository.save(order).toOrderResponseDto();
    }

    @Override
    public OrderResponseDto updateOrderStatus(int id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        order.setStatus(status);
        return orderRepository.save(order).toOrderResponseDto();
    }
}
