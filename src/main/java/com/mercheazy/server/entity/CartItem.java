package com.mercheazy.server.entity;

import com.mercheazy.server.dto.cart.CartItemResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Product product;

    @Column(name = "ci_quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Cart cart;

    public CartItemResponseDto toCartItemResponseDto() {
        return CartItemResponseDto.builder()
                .id(id)
                .productId(product.getId())
                .quantity(quantity)
                .build();
    }
}
