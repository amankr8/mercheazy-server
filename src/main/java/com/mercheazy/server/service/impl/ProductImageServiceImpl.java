package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.FileResponseDto;
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
    public List<FileResponseDto> getImagesByProduct(Product product) {
        return productImageRepository.findByProduct(product).stream().map(ProductImage::toFileResponseDto).toList();
    }

    @Override
    public List<FileResponseDto> saveImages(List<MultipartFile> imgFiles, Product product) {
        List<FileResponseDto> images = new ArrayList<>();
        imgFiles.forEach(imgFile -> {
            try {
                CloudinaryFile cloudinaryFile = cloudinaryService.uploadFile(imgFile, getFolderPath());
                ProductImage productImage = productImageRepository.save(buildProductImage(cloudinaryFile, product));
                images.add(productImage.toFileResponseDto());
            } catch (IOException e) {
                System.out.println("Error uploading image: " + e.getMessage());
            }
        });
        return images;
    }

    private ProductImage buildProductImage(CloudinaryFile cloudinaryFile, Product product) {
        return ProductImage.builder()
                .publicId(cloudinaryFile.getPublicId())
                .url(cloudinaryFile.getUrl())
                .product(product)
                .build();
    }
}
