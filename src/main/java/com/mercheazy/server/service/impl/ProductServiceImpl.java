package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.dto.ProductResponseDto;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.service.ProductImageService;
import com.mercheazy.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.save(productRequestDto.toProduct());
        List<String> imgUrls = new ArrayList<>();
        if (productRequestDto.getImages() != null) {
            imgUrls = productImageService.saveImages(productRequestDto.getImages(), product);
        }
        return product.toProductResponseDto(imgUrls);
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(product -> {
            List<String> imgUrls = productImageService.getImagesByProduct(product);
            return product.toProductResponseDto(imgUrls);
        }).toList();
    }

    @Override
    public ProductResponseDto getProductById(int id) {
        return productRepository.findById(id).map(product -> {
            List<String> imgUrls = productImageService.getImagesByProduct(product);
            return product.toProductResponseDto(imgUrls);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<String> imgUrls = productImageService.getImagesByProduct(product);

        return productRepository.save(productRequestDto.toProduct()).toProductResponseDto(imgUrls);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
