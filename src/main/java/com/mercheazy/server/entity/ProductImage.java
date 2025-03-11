package com.mercheazy.server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mercheazy.server.dto.FileResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pi_id")
    private int id;

    @Column(name = "pi_public_id")
    private String publicId;

    @Column(name = "pi_url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    @JsonBackReference
    private Product product;

    public FileResponseDto toFileResponseDto() {
        return FileResponseDto.builder()
                .id(id)
                .url(url)
                .build();
    }
}
