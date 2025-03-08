package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.StoreOwnerRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreOwnerRepository;
import com.mercheazy.server.service.StoreOwnerService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mercheazy.server.entity.StoreOwner.Role.CREATOR;

@RequiredArgsConstructor
@Service
public class StoreOwnerServiceImpl implements StoreOwnerService {
    private final StoreOwnerRepository storeOwnerRepository;

    @Override
    public StoreOwner addStoreOwner(StoreOwnerRequestDto storeOwnerRequestDto) {
        User user = AuthUtil.getLoggedInUser();
        StoreOwner storeOwner = getStoreOwnerByUser(user);
        Store store = storeOwner.getStore();
        if (store.getId() != storeOwnerRequestDto.getStoreId() || storeOwner.getRole() != CREATOR) {
            throw new IllegalArgumentException("User is unauthorized to add a store owner to this store.");
        }
        return storeOwnerRepository.save(storeOwnerRequestDto.toStoreCreator());
    }

    @Override
    public void saveStoreOwner(Store store) {
        StoreOwner storeOwner = StoreOwner.builder()
                .store(store)
                .user(AuthUtil.getLoggedInUser())
                .role(CREATOR)
                .build();
        storeOwnerRepository.save(storeOwner);
    }

    @Override
    public StoreOwner getStoreOwnerByUser(User user) {
        return storeOwnerRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not have a store."));
    }
}
