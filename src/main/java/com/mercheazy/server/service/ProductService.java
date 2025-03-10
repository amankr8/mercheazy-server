package com.mercheazy.server.service;

import com.mercheazy.server.dto.product.ProductRequestDto;
import com.mercheazy.server.dto.product.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getProducts();

    ProductResponseDto getProductById(int id);

    ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto);

    void deleteProduct(int id);
}
