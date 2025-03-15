package com.mercheazy.server.dto.user;

import lombok.Data;

@Data
public class ProfileRequestDto {
    private String name;
    private PhoneRequestDto phoneRequestDto;
    private AddressRequestDto addressRequestDto;
}
