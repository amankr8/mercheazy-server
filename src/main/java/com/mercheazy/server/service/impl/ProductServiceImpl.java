package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.dto.ProductResponseDto;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.service.ProductImageService;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductImageService productImageService;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Store store = storeService.getStoreByUser(AuthUtil.getLoggedInUser());
        Product product = productRepository.save(productRequestDto.toProduct(store));
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

        product.setName(productRequestDto.getName());
        product.setDesc(productRequestDto.getDesc());
        product.setStock(productRequestDto.getStock());
        product.setListPrice(productRequestDto.getListPrice());
        product.setSellPrice(productRequestDto.getSellPrice());

        return productRepository.save(product).toProductResponseDto(images);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
