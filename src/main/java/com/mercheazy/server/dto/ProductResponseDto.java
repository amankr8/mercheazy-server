package com.mercheazy.server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private double sellPrice;
    private double listPrice;
    private int stock;
    private Date createDate;
    private Date updateDate;
}
