package com.mercheazy.server.controller;

import com.mercheazy.server.dto.order.OrderRequestDto;
import com.mercheazy.server.entity.Order.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
public interface OrderController {
    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<?> updateOrderStatus(@PathVariable int id, @RequestBody OrderStatus orderStatus);
}
