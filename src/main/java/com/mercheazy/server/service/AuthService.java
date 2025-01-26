package com.mercheazy.server.service;

import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import com.mercheazy.server.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto signUp(SignupRequestDto signupRequestDto);

    UserResponseDto login(LoginRequestDto loginRequestDto);
}
