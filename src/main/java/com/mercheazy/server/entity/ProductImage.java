package com.mercheazy.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
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
}
