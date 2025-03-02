package com.mercheazy.server.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mercheazy.server.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public CloudinaryFile uploadFile(MultipartFile file, String folderPath) throws IOException {
        Map<?, ?> params = ObjectUtils.asMap(
                "folder", folderPath,
                "resource_type", "auto"
        );
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), params);

        return CloudinaryFile.builder()
                .publicId(result.get("public_id").toString())
                .url(result.get("secure_url").toString())
                .build();
    }

    @Override
    public void deleteFile(String publicId) throws IOException {
        Map<?, ?> params = ObjectUtils.asMap();
        Map<?, ?> deleteResult = cloudinary.uploader().destroy(publicId, params);

        if (!deleteResult.get("result").equals("ok")) {
            System.out.println("Could not delete file: " + deleteResult.get("result"));
        }
    }
}
