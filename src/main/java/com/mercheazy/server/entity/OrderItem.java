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
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_id")
    private int id;

    @Column(name = "oi_quantity", nullable = false)
    private int quantity;

    @Column(name = "oi__cost", nullable = false)
    private int price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_id")
    @JsonBackReference
    private Order order;

    public OrderItemResponseDto toOrderItemResponseDto() {
        return OrderItemResponseDto.builder()
                .id(id)
                .quantity(quantity)
                .price(price)
                .productId(product.getId())
                .build();
    }
}
