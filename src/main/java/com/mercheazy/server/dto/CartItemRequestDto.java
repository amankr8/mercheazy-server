package com.mercheazy.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemRequestDto {
    private int productId;
    private int quantity;
}
