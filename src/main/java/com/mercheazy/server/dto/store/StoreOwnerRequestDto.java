package com.mercheazy.server.dto.store;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.service.UserService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreOwnerRequestDto {
    private StoreService storeService;
    private UserService userService;

    private int storeId;
    private int userId;
    private StoreOwner.Role role;

    public StoreOwner toStoreCreator() {
        User user = userService.getUserById(userId);
        Store store = storeService.getStoreByUser(user);

        return StoreOwner.builder()
                .store(store)
                .user(user)
                .role(role)
                .build();
    }
}
