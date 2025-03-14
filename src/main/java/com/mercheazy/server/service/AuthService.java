package com.mercheazy.server.service;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.entity.user.AppUser;

public interface AuthService {
    AppUser signUp(SignupRequestDto signupRequestDto);

    AppUser login(LoginRequestDto loginRequestDto);
}
