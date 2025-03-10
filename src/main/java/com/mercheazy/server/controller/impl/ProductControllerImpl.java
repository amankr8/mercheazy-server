package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.ProductController;
import com.mercheazy.server.dto.product.ProductRequestDto;
import com.mercheazy.server.dto.product.ProductResponseDto;
import com.mercheazy.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ResponseEntity<?> createProduct(ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.createProduct(productRequestDto));
    }

    @Override
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Override
    public ResponseEntity<?> getProductById(int id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Override
    public ResponseEntity<?> getProductsByStoreId(int storeId) {
        return ResponseEntity.ok(productService.getProductsByStoreId(storeId));
    }

    @Override
    public ResponseEntity<?> updateProduct(int id, ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDto));
    }

    @Override
    public ResponseEntity<?> deleteProduct(int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
