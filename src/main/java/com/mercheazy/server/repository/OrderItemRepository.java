package com.mercheazy.server.repository;

import com.mercheazy.server.entity.order.MerchOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<MerchOrderItem, Integer> {
}
