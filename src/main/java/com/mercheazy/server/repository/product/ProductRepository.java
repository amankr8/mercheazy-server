package com.mercheazy.server.repository.product;

import com.mercheazy.server.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByStoreId(int storeId);
}
