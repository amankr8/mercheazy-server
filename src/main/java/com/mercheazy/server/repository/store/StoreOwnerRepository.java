package com.mercheazy.server.repository.store;

import com.mercheazy.server.entity.store.StoreOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreOwnerRepository extends JpaRepository<StoreOwner, Integer> {
    Optional<StoreOwner> findByStoreIdAndAuthUserId(int storeId, int userId);
    Optional<StoreOwner> findByAuthUserId(int userId);
    List<StoreOwner> findByStoreId(int storeId);
}
