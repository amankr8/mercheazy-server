package com.mercheazy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "store_creator")
public class StoreCreator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sc_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_id")
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "sc_role", nullable = false)
    @ColumnDefault("'CREATOR'")
    private Role role;

    public enum Role {
        CREATOR, MANAGER
    }
}
