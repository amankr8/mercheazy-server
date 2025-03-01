package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.dto.ProductResponseDto;
import com.mercheazy.server.entity.Product;
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
        return productRepository.save(productRequestDto.toProduct()).toProductResponseDto();
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(Product::toProductResponseDto).toList();
    }

    @Override
    public ProductResponseDto getProductById(int id) {
        return productRepository.findById(id).map(Product::toProductResponseDto).orElse(null);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        return productRepository.save(productRequestDto.toProduct()).toProductResponseDto();
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
