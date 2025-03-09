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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final StoreService storeService;
    private final CloudinaryService cloudinaryService;

    @Value("${spring.app.name}")
    private String APP_NAME;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Store store = storeService.getStoreByUser(AuthUtil.getLoggedInUser());
        Product product = productRepository.save(productRequestDto.toProduct(store));
        List<FileResponseDto> images = new ArrayList<>();
        if (productRequestDto.getImgFiles() != null) {
            images = saveImages(productRequestDto.getImgFiles(), product);
        }
        return product.toProductResponseDto(images);
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(product -> {
            List<FileResponseDto> images = getImagesByProduct(product);
            return product.toProductResponseDto(images);
        }).toList();
    }

    @Override
    public ProductResponseDto getProductResponseById(int id) {
        return productRepository.findById(id).map(product -> {
            List<FileResponseDto> images = getImagesByProduct(product);
            return product.toProductResponseDto(images);
        }).orElse(null);
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<FileResponseDto> images = getImagesByProduct(product);

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
