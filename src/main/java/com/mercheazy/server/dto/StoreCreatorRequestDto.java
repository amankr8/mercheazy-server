package com.mercheazy.server.dto;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.repository.StoreRepository;
import com.mercheazy.server.repository.UserRepository;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreCreatorRequestDto {
    private StoreRepository storeRepository;
    private UserRepository userRepository;

    private int storeId;
    private int userId;
    private StoreCreator.Role role;

    public StoreCreator toStoreCreator() {
        Store store = storeRepository.findById(storeId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (store == null || user == null) {
            throw new IllegalArgumentException("Store or User does not exist.");
        }

        return StoreCreator.builder()
                .store(store)
                .user(user)
                .role(role)
                .build();
    }
}
