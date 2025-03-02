package com.mercheazy.server.repository;

import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
}
