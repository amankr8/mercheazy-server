package com.mercheazy.server.controller;

import com.mercheazy.server.dto.order.OrderItemRequestDto;
import com.mercheazy.server.entity.order.MerchOrder.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
public interface OrderController {
    @PostMapping
    ResponseEntity<?> createOrder(@RequestBody OrderItemRequestDto orderItemRequestDto);

    @PostMapping("/checkout")
    ResponseEntity<?> checkoutCart();

    @GetMapping("/user-orders")
    ResponseEntity<?> getUserOrders();

    @PutMapping("/{id}")
    ResponseEntity<?> updateOrderStatus(@PathVariable int id, @RequestBody OrderStatus orderStatus);
}
