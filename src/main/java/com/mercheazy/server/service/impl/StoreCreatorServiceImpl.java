package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.StoreCreatorRequestDto;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.StoreCreatorRepository;
import com.mercheazy.server.service.StoreCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreCreatorServiceImpl implements StoreCreatorService {
    private final StoreCreatorRepository storeCreatorRepository;

    @Override
    public StoreCreator addStoreCreator(StoreCreatorRequestDto storeCreatorRequestDto) {
        return storeCreatorRepository.save(storeCreatorRequestDto.toStoreCreator());
    }

    @Override
    public StoreCreator getStoreCreatorByUser(User user) {
        return storeCreatorRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not have a store."));
    }
}
