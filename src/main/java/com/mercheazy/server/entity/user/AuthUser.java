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
import java.util.Optional;

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

    @Column(name = "au_email", unique = true, nullable = false)
    private String email;

    @Column(name = "au_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "au_role", nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(name = "au_create_date", nullable = false, updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "au_update_date", nullable = false)
    private Date updateDate;

    @JsonBackReference
    @OneToMany(mappedBy = "authUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles;

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
        Optional<Profile> defaultProfile = profiles.stream().filter(Profile::getPrimary).findFirst();
        String name = defaultProfile.map(Profile::getName).orElse("");

        return UserResponseDto.builder()
                .id(id)
                .username(username)
                .name(name)
                .email(email)
                .role(role)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
