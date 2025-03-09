package com.mercheazy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_id")
    private Long id;

    @Column(name = "oi_quantity", nullable = false)
    private int quantity;

    @Column(name = "oi__cost", nullable = false)
    private int price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_id")
    private Order order;
}
