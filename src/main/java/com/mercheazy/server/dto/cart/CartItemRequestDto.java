package com.mercheazy.server.dto.cart;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private int productId;
    private int quantity;
}
