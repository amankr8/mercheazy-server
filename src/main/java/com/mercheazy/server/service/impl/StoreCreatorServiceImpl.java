package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.StoreCreatorRequestDto;
import com.mercheazy.server.entity.StoreCreator;
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
}
