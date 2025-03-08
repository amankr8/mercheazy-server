package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreCreator;
import com.mercheazy.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreCreatorRepository extends JpaRepository<StoreCreator, Integer> {
    Optional<StoreCreator> findByUser(User user);
    List<StoreCreator> findByStore(Store store);
}
