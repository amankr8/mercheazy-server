package com.mercheazy.server.dto;

import com.mercheazy.server.entity.Product;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class ProductRequestDto {

    private String name;
    private String desc;
    private double actualPrice;
    private double sellPrice;
    private int stock;
    private List<MultipartFile> images;

    public Product toProduct() {
        return Product.builder()
                .name(name)
                .desc(desc)
                .actualPrice(actualPrice)
                .sellPrice(sellPrice)
                .stock(stock)
                .createDate(new Date())
                .updateDate(new Date())
                .build();
    }
}
