package com.mercheazy.server.dto.store;

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
    private List<Integer> storeOwnerUserIds;
    private Date createDate;
    private Date updateDate;
}
