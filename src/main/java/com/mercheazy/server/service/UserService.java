package com.mercheazy.server.service;

import com.mercheazy.server.dto.UserResponseDto;
import com.mercheazy.server.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(int id);
}
