package com.mercheazy.server.repository.store;

import com.mercheazy.server.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
