package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Store;
import com.mercheazy.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
