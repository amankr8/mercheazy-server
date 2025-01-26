package com.mercheazy.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusResponseDto {
    private int status;
    private String message;
}
