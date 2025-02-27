package com.mercheazy.server.dto;

import lombok.Data;

@Data
public class ProductRequestDto {

    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private Long categoryId;
}
