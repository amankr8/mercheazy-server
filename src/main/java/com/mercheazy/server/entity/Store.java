package com.mercheazy.server.entity;

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

    public StoreResponseDto toStoreResponseDto() {
        List<Integer> storeOwnerUserIds = storeOwners.stream()
                .map(storeOwner -> storeOwner.getUser().getId()).toList();
        return StoreResponseDto.builder()
                .id(id)
                .name(name)
                .desc(desc)
                .storeOwnerUserIds(storeOwnerUserIds)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
