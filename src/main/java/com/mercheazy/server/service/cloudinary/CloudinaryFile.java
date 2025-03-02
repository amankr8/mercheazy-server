package com.mercheazy.server.service.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CloudinaryFile {
    private String publicId;
    private String url;
}
