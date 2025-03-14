package com.mercheazy.server.service;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.entity.order.MerchOrder;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;

import java.util.List;

public interface OrderService {
    MerchOrder placeOrder(OrderItemRequestDto orderItemRequestDto);

    MerchOrder checkoutOrderByProfileId(int profileId);

    List<MerchOrder> getOrdersByUser(int userId);

    MerchOrder getOrderById(int id);

    MerchOrder updateOrderStatus(int orderId, OrderStatus newStatus);
}
