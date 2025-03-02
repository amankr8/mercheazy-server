package com.mercheazy.server.service;

import com.mercheazy.server.dto.FileResponseDto;
import com.mercheazy.server.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    List<FileResponseDto> getImagesByProduct(Product product);

    List<FileResponseDto> saveImages(List<MultipartFile> imgFiles, Product product);
}
