package com.mercheazy.server.service;

import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.dto.ProductRequestDto;
import com.mercheazy.server.dto.ProductResponseDto;
import com.mercheazy.server.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getProducts();

    ProductResponseDto getProductById(int id);

    ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto);

    void deleteProduct(int id);

    List<FileResponseDto> getImagesByProduct(Product product);

    List<FileResponseDto> saveImages(List<MultipartFile> imgFiles, Product product);
}
