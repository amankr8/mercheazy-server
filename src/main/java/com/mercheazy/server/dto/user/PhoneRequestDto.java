package com.mercheazy.server.dto.user;

import lombok.Data;

@Data
public class PhoneRequestDto {
    private String countryCode;
    private String number;
}
