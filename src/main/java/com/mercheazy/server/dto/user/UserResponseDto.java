package com.mercheazy.server.dto.user;

import com.mercheazy.server.entity.User.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserResponseDto {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Date createDate;
    private Date updateDate;
}
