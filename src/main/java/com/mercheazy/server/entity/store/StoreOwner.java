package com.mercheazy.server.entity.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mercheazy.server.dto.store.StoreOwnerResponseDto;
import com.mercheazy.server.entity.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "au_id")
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "so_role", nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(name = "so_create_date", nullable = false, updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "so_update_date", nullable = false)
    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_id")
    @JsonManagedReference
    private Store store;

    public enum Role {
        CREATOR, MANAGER
    }

    public StoreOwnerResponseDto toStoreOwnerResponseDto() {
        return StoreOwnerResponseDto.builder()
                .id(id)
                .userId(appUser.getId())
                .role(role)
                .build();
    }
}
