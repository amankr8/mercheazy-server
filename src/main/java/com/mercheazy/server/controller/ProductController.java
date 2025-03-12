package com.mercheazy.server.controller;

import com.mercheazy.server.dto.product.ProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
public interface ProductController {

    @PostMapping
    ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductRequestDto productRequestDto);

    @GetMapping
    ResponseEntity<?> getProducts();

    @GetMapping("/{id}")
    ResponseEntity<?> getProductById(@PathVariable int id);

    @GetMapping("/store/{storeId}")
    ResponseEntity<?> getProductsByStoreId(@PathVariable int storeId);

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDto productRequestDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable int id);
}
