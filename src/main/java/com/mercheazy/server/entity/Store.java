package com.mercheazy.server.entity;

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
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id")
    private int id;

    @Column(name = "s_name", nullable = false)
    private String name;

    @Column(name = "s_desc", nullable = false)
    private String desc;

    @Column(name = "s_create_date", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createDate = new Date();

    @Column(name = "s_update_date", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date updateDate = new Date();
}
