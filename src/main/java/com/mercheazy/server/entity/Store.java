package com.mercheazy.server.entity;

import com.mercheazy.server.dto.store.StoreOwnerResponseDto;
import com.mercheazy.server.dto.store.StoreResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @CreationTimestamp
    @Column(name = "s_create_date", nullable = false, updatable = false)
    private Date createDate = new Date();

    @UpdateTimestamp
    @Column(name = "s_update_date", nullable = false)
    private Date updateDate = new Date();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreOwner> storeOwners;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public StoreResponseDto toStoreResponseDto() {
        return StoreResponseDto.builder()
                .id(id)
                .name(name)
                .desc(desc)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
