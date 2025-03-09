package com.mercheazy.server.dto.cart;

import com.mercheazy.server.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponseDto {
    private int id;
    private ProductResponseDto productResponseDto;
    private int quantity;
}
