package com.mercheazy.server.dto;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.service.UserService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreCreatorRequestDto {
    private StoreService storeService;
    private UserService userService;

    private int storeId;
    private int userId;
    private StoreCreator.Role role;

    public StoreCreator toStoreCreator() {
        User user = userService.getUserById(userId);
        Store store = storeService.getStoreByUser(user);

        return StoreCreator.builder()
                .store(store)
                .user(user)
                .role(role)
                .build();
    }
}
