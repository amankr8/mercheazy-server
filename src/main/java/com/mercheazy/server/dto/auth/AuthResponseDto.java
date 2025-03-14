package com.mercheazy.server.dto.auth;

import com.mercheazy.server.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private UserResponseDto user;
    private String message;
}
