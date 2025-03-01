package com.mercheazy.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private Double sellPrice;
    private Double actualPrice;
    private int stock;
}
