package com.mercheazy.server.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    List<OrderItemRequestDto> orderItems;
}
