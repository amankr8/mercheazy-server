package com.mercheazy.server.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private int storeId;
    private int totalPrice;
    List<OrderItemRequestDto> orderItems;
}
