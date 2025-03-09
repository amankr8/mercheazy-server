package com.mercheazy.server.dto.product;

import com.mercheazy.server.dto.FileResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ProductResponseDto {
    private int id;
    private String name;
    private String desc;
    private double sellPrice;
    private double listPrice;
    private int stock;
    private List<FileResponseDto> images;
    private Date createDate;
    private Date updateDate;
}
