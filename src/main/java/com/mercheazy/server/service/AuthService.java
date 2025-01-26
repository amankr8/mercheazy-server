package com.mercheazy.server.service;

import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import com.mercheazy.server.dto.UserResponseDto;
import com.mercheazy.server.entity.User;

public interface AuthService {

    UserResponseDto signUp(SignupRequestDto signupRequestDto);

    User login(LoginRequestDto loginRequestDto);
}
