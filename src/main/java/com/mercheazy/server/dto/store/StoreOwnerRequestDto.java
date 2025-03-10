package com.mercheazy.server.dto.store;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.service.UserService;
import lombok.Builder;
import lombok.Data;

@Data
public class StoreOwnerRequestDto {
    private int storeId;
    private int userId;
    private StoreOwner.Role role;
}
