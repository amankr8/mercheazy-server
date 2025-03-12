package com.mercheazy.server.controller.impl;

import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.entity.Order.OrderStatus;
import com.mercheazy.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerImpl implements com.mercheazy.server.controller.OrderController {
    private final OrderService orderService;

    @Override
    public ResponseEntity<?> createOrder(OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
    }

    @Override
    public ResponseEntity<?> updateOrderStatus(int id, OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    }
}
