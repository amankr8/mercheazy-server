package com.mercheazy.server.dto;

import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.Store;
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
    private List<MultipartFile> imgFiles;

    public Product toProduct(Store store) {
        return Product.builder()
                .name(name)
                .desc(desc)
                .listPrice(listPrice)
                .sellPrice(sellPrice)
                .stock(stock)
                .store(store)
                .build();
    }
}
