package com.mercheazy.server.dto.user;

import com.mercheazy.server.entity.AppUser;
import com.mercheazy.server.entity.AppUser.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    private Role role;

    public AppUser toUser() {
        Role role = this.role == null ? Role.USER : this.role;
        return AppUser.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username((firstName + lastName).toLowerCase())
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}

