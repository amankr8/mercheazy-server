package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.dto.product.ProductRequestDto;
import com.mercheazy.server.dto.product.ProductResponseDto;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.ProductImage;
import com.mercheazy.server.entity.Store;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.model.CloudinaryFile;
import com.mercheazy.server.repository.ProductImageRepository;
import com.mercheazy.server.repository.ProductRepository;
import com.mercheazy.server.service.CloudinaryService;
import com.mercheazy.server.service.ProductService;
import com.mercheazy.server.service.StoreService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final CloudinaryService cloudinaryService;

    @Value("${spring.app.name}")
    private String APP_NAME;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Store store = storeService.getStoreByUser(AuthUtil.getLoggedInUser());
        Product product = Product.builder()
                .name(productRequestDto.getName())
                .desc(productRequestDto.getDesc())
                .listPrice(productRequestDto.getListPrice())
                .sellPrice(productRequestDto.getSellPrice())
                .stock(productRequestDto.getStock())
                .store(store)
                .build();

        product = productRepository.save(product);

        List<ProductImage> productImages = saveImages(productRequestDto.getImgFiles(), product);
        if (!productImages.isEmpty()) {
            product.setProductImages(productImages);
            productRepository.save(product);
        }

        return product.toProductResponseDto();
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(Product::toProductResponseDto).toList();
    }

    @Override
    public ProductResponseDto getProductResponseById(int id) {
        return productRepository.findById(id).map(Product::toProductResponseDto).orElse(null);
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(productRequestDto.getName());
        product.setDesc(productRequestDto.getDesc());
        product.setStock(productRequestDto.getStock());
        product.setListPrice(productRequestDto.getListPrice());
        product.setSellPrice(productRequestDto.getSellPrice());

        return productRepository.save(product).toProductResponseDto();
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    private List<ProductImage> saveImages(List<MultipartFile> imgFiles, Product product) {
        if (imgFiles == null || imgFiles.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductImage> productImages = new ArrayList<>();
        String folderPath = APP_NAME + "/products";

        imgFiles.forEach(imgFile -> {
            try {
                CloudinaryFile cloudinaryFile = cloudinaryService.uploadFile(imgFile, folderPath);
                ProductImage productImage = ProductImage.builder()
                        .publicId(cloudinaryFile.getPublicId())
                        .url(cloudinaryFile.getUrl())
                        .product(product)
                        .build();
                productImages.add(productImage);
            } catch (IOException e) {
                log.error("Error uploading image {}: {}", imgFile.getOriginalFilename(), e.getMessage(), e);
            }
        });

        return productImages;
    }

}
