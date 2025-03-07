package com.mercheazy.server.entity;

import com.mercheazy.server.dto.UserResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
@Table(name = "merch_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private int id;

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

    @CreationTimestamp
    @Column(name = "u_create_date", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "u_update_date", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "u_role", nullable = false)
    @ColumnDefault("'USER'")
    private Role role;

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

    public enum Role {
        ADMIN, USER
    }

    public UserResponseDto toUserResponseDto() {
        return UserResponseDto.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
