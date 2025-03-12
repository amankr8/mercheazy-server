package com.mercheazy.server.repository;

import com.mercheazy.server.entity.MerchOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<MerchOrderItem, Integer> {
}
