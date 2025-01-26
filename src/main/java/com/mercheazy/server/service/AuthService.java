package com.mercheazy.server.service;

import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import com.mercheazy.server.entity.User;

public interface AuthService {

    User signUp(SignupRequestDto signupRequestDto);

    User signIn(LoginRequestDto loginRequestDto);
}
