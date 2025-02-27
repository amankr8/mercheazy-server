package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.dto.ProductResponseDto;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        return null;
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
