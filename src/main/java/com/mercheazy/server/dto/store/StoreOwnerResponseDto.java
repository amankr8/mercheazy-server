package com.mercheazy.server.dto.store;

import com.mercheazy.server.entity.StoreOwner;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreOwnerResponseDto {
    private int id;
    private int storeId;
    private int userId;
    private StoreOwner.Role role;
}
