package com.mercheazy.server.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_token")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ut_id")
    private int id;

    @Column(name = "ut_token", nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "au_id")
    private AuthUser authUser;
}
