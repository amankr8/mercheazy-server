package com.mercheazy.server.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartResponseDto {
    private int id;
    private List<CartItemResponseDto> cartItems;
}
