package com.mercheazy.server.dto.store;

import com.mercheazy.server.entity.store.StoreOwner;
import lombok.Data;

@Data
public class StoreOwnerRequestDto {
    private int userId;
    private StoreOwner.Role role;
}
