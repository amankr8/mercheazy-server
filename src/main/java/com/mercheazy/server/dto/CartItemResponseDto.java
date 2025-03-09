package com.mercheazy.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponseDto {
    private ProductResponseDto productResponseDto;
    private int quantity;
}
