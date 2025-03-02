package com.mercheazy.server.service;

import com.mercheazy.server.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    List<String> getImagesByProduct(Product product);

    List<String> saveImages(List<MultipartFile> images, Product product);
}
