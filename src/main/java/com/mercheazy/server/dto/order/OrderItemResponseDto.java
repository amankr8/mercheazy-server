package com.mercheazy.server.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDto {
    private int id;
    private int productId;
    private int quantity;
    private double price;
}
