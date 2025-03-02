package com.mercheazy.server.entity;

import com.mercheazy.server.dto.ProductResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

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
    private double listPrice;

    @Column(name = "p_sell_price", nullable = false)
    private double sellPrice;

    @Column(name = "p_stock", nullable = false)
    private int stock;

    @CreationTimestamp
    @Column(name = "p_create_date", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "p_update_date", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_id")
    private Store store;

    public ProductResponseDto toProductResponseDto(List<String> imgUrls) {
        return ProductResponseDto.builder()
                .id(id)
                .name(name)
                .desc(desc)
                .sellPrice(sellPrice)
                .listPrice(listPrice)
                .stock(stock)
                .createDate(createDate)
                .updateDate(updateDate)
                .imgUrls(imgUrls)
                .build();
    }
}
