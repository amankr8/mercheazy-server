package com.mercheazy.server.service.impl;

import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.ProductImage;
import com.mercheazy.server.repository.ProductImageRepository;
import com.mercheazy.server.service.CloudinaryService;
import com.mercheazy.server.service.ProductImageService;
import com.mercheazy.server.model.CloudinaryFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductImageServiceImpl implements ProductImageService {
    private final CloudinaryService cloudinaryService;
    private final ProductImageRepository productImageRepository;

    @Value("${spring.app.name}")
    private String APP_NAME;

    private String getFolderPath() {
        return APP_NAME + "/products";
    }

    @Override
    public List<String> getImagesByProduct(Product product) {
        return productImageRepository.findByProduct(product).stream().map(ProductImage::getUrl).toList();
    }

    @Override
    public List<String> saveImages(List<MultipartFile> images, Product product) {
        List<String> imageUrls = new ArrayList<>();
        images.forEach(image -> {
            try {
                CloudinaryFile cloudinaryFile = cloudinaryService.uploadFile(image, getFolderPath());
                productImageRepository.save(toProductImage(cloudinaryFile, product));
                imageUrls.add(cloudinaryFile.getUrl());
            } catch (IOException e) {
                System.out.println("Error uploading image: " + e.getMessage());
            }
        });
        return imageUrls;
    }

    private ProductImage toProductImage(CloudinaryFile cloudinaryFile, Product product) {
        return ProductImage.builder()
                .publicId(cloudinaryFile.getPublicId())
                .url(cloudinaryFile.getUrl())
                .product(product)
                .build();
    }
}
