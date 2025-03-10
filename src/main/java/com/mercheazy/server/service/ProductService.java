package com.mercheazy.server.service;

import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.dto.product.ProductRequestDto;
import com.mercheazy.server.dto.product.ProductResponseDto;
import com.mercheazy.server.entity.Product;
import com.mercheazy.server.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getProducts();

    ProductResponseDto getProductResponseById(int id);

    Product getProductById(int productId);

    ProductResponseDto updateProduct(int id, ProductRequestDto productRequestDto);

    void deleteProduct(int id);
}
