package com.mercheazy.server.service;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(OrderItemRequestDto orderItemRequestDto);

    OrderResponseDto checkoutCartByUserId(int userId);

    List<OrderResponseDto> getOrdersByUser(int userId);

    OrderResponseDto updateOrderStatus(int id, OrderStatus status);
}
