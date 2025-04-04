package com.mercheazy.server.repository.order;

import com.mercheazy.server.entity.order.MerchOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<MerchOrder, Integer> {
    List<MerchOrder> findByProfileId(int userId);
}
