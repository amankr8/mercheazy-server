package com.mercheazy.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "mercheazy_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_username", unique = true, nullable = false)
    private String username;

    @Column(name = "u_first_name", nullable = false)
    @ColumnDefault("'User'")
    private String firstName;

    @Column(name = "u_last_name")
    private String lastName;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_email", unique = true, nullable = false)
    private String email;

    @Column(name = "u_create_date", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createDate = new Date();

    @Column(name = "u_update_date", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date updateDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "u_role", nullable = false)
    @ColumnDefault("'USER'")
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
