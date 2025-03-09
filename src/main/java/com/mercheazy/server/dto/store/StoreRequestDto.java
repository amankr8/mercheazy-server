package com.mercheazy.server.dto.store;

import com.mercheazy.server.entity.Store;
import lombok.Data;

@Data
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
