package com.mercheazy.server.repository;

import com.mercheazy.server.entity.MerchOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<MerchOrder, Integer> {
    List<MerchOrder> findByAppUserId(int userId);
}
