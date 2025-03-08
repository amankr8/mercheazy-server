package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.StoreOwner;
import com.mercheazy.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Integer> {
    Optional<StoreOwner> findByUser(User user);
    List<StoreOwner> findByStore(Store store);
}
