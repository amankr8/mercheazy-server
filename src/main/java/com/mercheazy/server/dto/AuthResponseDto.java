package com.mercheazy.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String jwtToken;
    private UserResponseDto user;
    private String message;
}
