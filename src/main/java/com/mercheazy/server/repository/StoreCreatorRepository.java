package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreCreatorRepository extends JpaRepository<StoreCreator, Integer> {
    Optional<StoreCreator> findByUser(User user);
    Optional<StoreCreator> findByStore(Store store);
}
