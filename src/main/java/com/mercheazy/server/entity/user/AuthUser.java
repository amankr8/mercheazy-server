package com.mercheazy.server.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mercheazy.server.dto.user.UserResponseDto;
import jakarta.persistence.*;
import lombok.*;
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
@NoArgsConstructor
@Entity
@Table(name = "auth_user")
public class AuthUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "au_id")
    private int id;

    @Column(name = "au_username", unique = true, nullable = false)
    private String username;

    @Column(name = "au_password", nullable = false)
    private String password;

    @Column(name = "au_email", unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "au_create_date", nullable = false, updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "au_update_date", nullable = false)
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "au_role", nullable = false)
    @ColumnDefault("'USER'")
    private Role role;

    @JsonBackReference
    @OneToMany(mappedBy = "authUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

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
                .email(email)
                .role(role)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
