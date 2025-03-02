package com.mercheazy.server.service;

import com.mercheazy.server.model.CloudinaryFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    CloudinaryFile uploadFile(MultipartFile file, String folderPath) throws IOException;

    void deleteFile(String publicId) throws IOException;
}
