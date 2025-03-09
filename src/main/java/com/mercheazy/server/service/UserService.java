package com.mercheazy.server.service;

import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import com.mercheazy.server.dto.UserResponseDto;
import com.mercheazy.server.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto signUp(SignupRequestDto signupRequestDto);

    UserResponseDto login(LoginRequestDto loginRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserDetailsById(int id);

    User getUserById(int id);
}
