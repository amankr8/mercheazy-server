package com.mercheazy.server.service;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.entity.user.AuthUser;

public interface AuthService {
    AuthUser signUp(SignupRequestDto signupRequestDto);

    AuthUser login(LoginRequestDto loginRequestDto);

    AuthUser googleLogin(String code);
}
