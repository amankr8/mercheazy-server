package com.mercheazy.server.controller.impl;

import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.order.MerchOrder;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;
import com.mercheazy.server.service.OrderService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements com.mercheazy.server.controller.OrderController {
    private final OrderService orderService;

    @Override
    public ResponseEntity<?> createOrder(OrderRequestDto orderRequestDto) {
        OrderResponseDto order = orderService.placeOrder(orderRequestDto).toOrderResponseDto();
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<?> checkoutCart() {
        OrderResponseDto order = orderService.checkoutOrderByProfileId(AuthUtil.getLoggedInUser().getId()).toOrderResponseDto();
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<?> getUserOrders() {
        List<OrderResponseDto> userOrders = orderService.getOrdersByUser(AuthUtil.getLoggedInUser().getId())
                .stream().map(MerchOrder::toOrderResponseDto).toList();
        return ResponseEntity.ok(userOrders);
    }

    @Override
    public ResponseEntity<?> updateOrderStatus(int id, OrderStatus orderStatus) {
        OrderResponseDto order = orderService.updateOrderStatus(id, orderStatus).toOrderResponseDto();
        return ResponseEntity.ok(order);
    }
}
