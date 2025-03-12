package com.mercheazy.server.service;

import com.mercheazy.server.dto.user.LoginRequestDto;
import com.mercheazy.server.dto.user.SignupRequestDto;
import com.mercheazy.server.dto.user.UserResponseDto;
import com.mercheazy.server.entity.user.AppUser;

import java.util.List;

public interface UserService {

    UserResponseDto signUp(SignupRequestDto signupRequestDto);

    UserResponseDto login(LoginRequestDto loginRequestDto);

    List<UserResponseDto> getAllUsers();

    AppUser getUserById(int id);
}
