package com.mercheazy.server.entity;

import com.mercheazy.server.dto.ProductResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_id")
    private int id;

    @Column(name = "p_name", nullable = false)
    private String name;

    @Column(name = "p_desc", nullable = false)
    private String desc;

    @Column(name = "p_actual_price", nullable = false)
    private double actualPrice;

    @Column(name = "p_sell_price", nullable = false)
    private double sellPrice;

    @Column(name = "p_stock", nullable = false)
    private int stock;

    @Column(name = "p_create_date", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createDate = new Date();

    @Column(name = "p_update_date", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date updateDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_id")
    private Store store;

    public ProductResponseDto toProductResponseDto() {
        return ProductResponseDto.builder()
                .id(id)
                .name(name)
                .description(desc)
                .sellPrice(sellPrice)
                .actualPrice(actualPrice)
                .stock(stock)
                .build();
    }
}
