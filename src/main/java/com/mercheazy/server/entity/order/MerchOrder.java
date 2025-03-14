package com.mercheazy.server.entity.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mercheazy.server.dto.order.OrderResponseDto;
import com.mercheazy.server.entity.user.Profile;
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
public class MerchOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mo_id")
    private int id;

    @CreationTimestamp
    @Column(name = "mo_create_date", nullable = false, updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "mo_update_date", nullable = false)
    private Date updateDate;

    @Column(name = "mo_total_price", nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "mo_status", nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pr_id")
    private Profile profile;

    @OneToMany(mappedBy = "merchOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<MerchOrderItem> merchOrderItems;

    public enum OrderStatus {
        PLACED, CREATED, SHIPPED, DELIVERED, CANCELLED
    }

    public OrderResponseDto toOrderResponseDto() {
        return OrderResponseDto.builder()
                .id(id)
                .createDate(createDate)
                .updateDate(updateDate)
                .totalPrice(totalPrice)
                .status(status)
                .orderItems(merchOrderItems.stream()
                        .map(MerchOrderItem::toOrderItemResponseDto)
                        .toList())
                .build();
    }
}
