package com.mercheazy.server.dto;

import com.mercheazy.server.entity.Store;

public class StoreRequestDto {
    private String name;
    private String desc;

    public Store toStore() {
        return Store.builder()
                .name(name)
                .desc(desc)
                .build();
    }
}
