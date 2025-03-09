package com.mercheazy.server.controller;

import com.mercheazy.server.dto.product.ProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/product")
public interface ProductController {

    @PostMapping
    ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductRequestDto productRequestDto);

    @GetMapping
    ResponseEntity<?> getProducts();

    @GetMapping("/{id}")
    ResponseEntity<?> getProductById(@PathVariable int id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDto productRequestDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable int id);
}
