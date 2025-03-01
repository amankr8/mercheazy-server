package com.mercheazy.server.controller;

import com.mercheazy.server.dto.ProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
public interface ProductController {

    @PostMapping
    ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto);

    @GetMapping
    ResponseEntity<?> getProducts();

    @GetMapping("/{id}")
    ResponseEntity<?> getProductById(@PathVariable int id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable int id);
}
