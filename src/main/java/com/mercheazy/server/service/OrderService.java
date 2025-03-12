package com.mercheazy.server.service;

import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.Order.OrderStatus;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto updateOrderStatus(int id, OrderStatus status);
}
