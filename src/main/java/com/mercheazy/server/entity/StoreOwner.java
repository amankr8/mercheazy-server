package com.mercheazy.server.entity;

import com.mercheazy.server.dto.store.StoreOwnerResponseDto;
import com.mercheazy.server.dto.store.StoreResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_owner")
public class StoreOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "so_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "so_role", nullable = false)
    @ColumnDefault("'CREATOR'")
    private Role role;

    public enum Role {
        CREATOR, MANAGER
    }

    public StoreOwnerResponseDto toStoreOwnerResponseDto() {
        return StoreOwnerResponseDto.builder()
                .id(id)
                .storeId(store.getId())
                .userId(user.getId())
                .role(role)
                .build();
    }
}
