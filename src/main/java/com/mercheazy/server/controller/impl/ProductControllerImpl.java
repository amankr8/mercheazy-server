package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.ProductController;
import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private ProductService productService;

    @Override
    public ResponseEntity<?> createProduct(ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProducts() {
        return null;
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateProduct(Long id, ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        return null;
    }
}
