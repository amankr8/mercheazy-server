package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByStore(Store store);
}
