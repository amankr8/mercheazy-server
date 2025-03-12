package com.mercheazy.server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mercheazy.server.dto.order.OrderItemResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merch_order_item")
public class MerchOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moi_id")
    private int id;

    @Column(name = "moi_quantity", nullable = false)
    private int quantity;

    @Column(name = "moi_price", nullable = false)
    private double price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mo_id")
    @JsonBackReference
    private MerchOrder merchOrder;

    public OrderItemResponseDto toOrderItemResponseDto() {
        return OrderItemResponseDto.builder()
                .id(id)
                .quantity(quantity)
                .price(price)
                .productId(product.getId())
                .build();
    }
}
