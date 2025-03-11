package com.mercheazy.server.dto.order;

import java.util.List;

public class OrderRequestDto {
    private int storeId;
    List<OrderItemRequestDto> orderItems;
}
