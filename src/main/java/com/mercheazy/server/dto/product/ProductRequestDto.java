package com.mercheazy.server.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequestDto {
    private String name;
    private String desc;
    private double listPrice;
    private double sellPrice;
    private int stock;
    private int storeId;
    private List<MultipartFile> imgFiles;
}
