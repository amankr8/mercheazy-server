package com.mercheazy.server.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.url}")
    private String CLOUDINARY_URL;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(CLOUDINARY_URL);
    }
}
