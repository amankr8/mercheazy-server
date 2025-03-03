package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.dto.ProductResponseDto;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.exception.ResourceNotFoundException;
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
        List<FileResponseDto> images = new ArrayList<>();
        if (productRequestDto.getImgFiles() != null) {
            images = productImageService.saveImages(productRequestDto.getImgFiles(), product);
        }
        return product.toProductResponseDto(images);
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(product -> {
            List<FileResponseDto> images = productImageService.getImagesByProduct(product);
            return product.toProductResponseDto(images);
        }).toList();
    }

    @Override
    public ProductResponseDto getProductById(int id) {
        return productRepository.findById(id).map(product -> {
            List<FileResponseDto> images = productImageService.getImagesByProduct(product);
            return product.toProductResponseDto(images);
        }).orElse(null);
    }

    @Override
    public ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<FileResponseDto> images = productImageService.getImagesByProduct(product);

        return productRepository.save(productRequestDto.toProduct()).toProductResponseDto(images);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
