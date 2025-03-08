package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.StoreCreatorRequestDto;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreCreatorRepository;
import com.mercheazy.server.service.StoreCreatorService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mercheazy.server.entity.StoreCreator.Role.CREATOR;

@RequiredArgsConstructor
@Service
public class StoreCreatorServiceImpl implements StoreCreatorService {
    private final StoreCreatorRepository storeCreatorRepository;

    @Override
    public StoreCreator addStoreCreator(StoreCreatorRequestDto storeCreatorRequestDto) {
        return storeCreatorRepository.save(storeCreatorRequestDto.toStoreCreator());
    }

    @Override
    public void saveStoreCreator(Store store) {
        StoreCreator storeCreator = StoreCreator.builder()
                .store(store)
                .user(AuthUtil.getLoggedInUser())
                .role(CREATOR)
                .build();
        storeCreatorRepository.save(storeCreator);
    }

    @Override
    public StoreCreator getStoreCreatorByUser(User user) {
        return storeCreatorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not have a store."));
    }
}
