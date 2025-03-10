package com.mercheazy.server.dto.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponseDto {
    private int id;
    private int productId;
    private int quantity;
}
