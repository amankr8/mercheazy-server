package com.mercheazy.server.repository;

import com.mercheazy.server.entity.store.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Integer> {
    Optional<StoreOwner> findByStoreIdAndAppUserId(int storeId, int userId);
    Optional<StoreOwner> findByAppUserId(int userId);
    List<StoreOwner> findByStoreId(int storeId);
}
