package com.mercheazy.server.dto.store;

import com.mercheazy.server.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class StoreResponseDto {
    private int id;
    private String name;
    private String desc;
    private List<StoreOwnerResponseDto> storeOwners;
    private List<ProductResponseDto> products;
    private Date createDate;
    private Date updateDate;
}
