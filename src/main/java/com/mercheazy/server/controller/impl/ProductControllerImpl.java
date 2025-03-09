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
        ProductResponseDto product = productService.createProduct(productRequestDto);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<?> getProducts() {
        List<ProductResponseDto> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<?> getProductById(int id) {
        ProductResponseDto product = productService.getProductResponseById(id);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<?> updateProduct(int id, ProductRequestDto productRequestDto) {
        ProductResponseDto updatedProduct = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @Override
    public ResponseEntity<?> deleteProduct(int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
