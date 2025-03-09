package com.mercheazy.server.entity;

import com.mercheazy.server.dto.CartItemResponseDto;
import com.mercheazy.server.dto.FileResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Product product;

    @Column(name = "ci_quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Cart cart;

    public CartItemResponseDto toCartItemResponseDto(List<FileResponseDto> images) {
        return CartItemResponseDto.builder()
                .productResponseDto(product.toProductResponseDto(images))
                .quantity(quantity)
                .build();
    }
}
