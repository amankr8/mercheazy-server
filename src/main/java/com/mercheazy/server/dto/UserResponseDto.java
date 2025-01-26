package com.mercheazy.server.dto;

import com.mercheazy.server.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponseDto {

    private String username;
    private String email;
    private Role role;
    private Date createDate;
    private Date updateDate;
}
