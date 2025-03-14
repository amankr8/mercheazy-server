package com.mercheazy.server.service;

import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.entity.order.MerchOrder;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;

import java.util.List;

public interface OrderService {
    MerchOrder placeOrder(OrderRequestDto orderRequestDto);

    MerchOrder checkoutOrderByProfileId(int profileId);

    List<MerchOrder> getOrdersByUser(int userId);

    MerchOrder getOrderById(int id);

    MerchOrder updateOrderStatus(int orderId, OrderStatus newStatus);
}
