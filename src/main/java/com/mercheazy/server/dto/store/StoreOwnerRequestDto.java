package com.mercheazy.server.dto.store;

import com.mercheazy.server.entity.StoreOwner;
import lombok.Data;

@Data
public class StoreOwnerRequestDto {
    private int userId;
    private StoreOwner.Role role;
}
