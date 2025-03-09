package com.mercheazy.server.dto.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemRequestDto {
    private int productId;
    private int quantity;
}
