package com.mercheazy.server.dto.user;

import lombok.Data;

@Data
public class AddressRequestDto {
    private String house;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zip;
}
