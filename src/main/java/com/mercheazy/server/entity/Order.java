package com.mercheazy.server.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercheazy.server.dto.order.OrderResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merch_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    private int id;

    @CreationTimestamp
    @Column(name = "o_create_date", nullable = false, updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "o_update_date", nullable = false)
    private Date updateDate;

    @Column(name = "o_total_price", nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "o_status", nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public enum OrderStatus {
        PENDING, CREATED, PLACED, SHIPPED, DELIVERED, CANCELLED
    }

    public OrderResponseDto toOrderResponseDto() {
        return OrderResponseDto.builder()
                .id(id)
                .createDate(createDate)
                .updateDate(updateDate)
                .totalPrice(totalPrice)
                .status(status)
                .orderItems(orderItems.stream()
                        .map(OrderItem::toOrderItemResponseDto)
                        .toList())
                .build();
    }
}
