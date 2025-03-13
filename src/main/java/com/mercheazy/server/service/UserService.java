package com.mercheazy.server.service;

import com.mercheazy.server.dto.user.LoginRequestDto;
import com.mercheazy.server.dto.user.SignupRequestDto;
import com.mercheazy.server.dto.user.UserResponseDto;
import com.mercheazy.server.entity.user.AppUser;

import java.util.List;

public interface UserService {

    AppUser signUp(SignupRequestDto signupRequestDto);

    AppUser login(LoginRequestDto loginRequestDto);

    List<AppUser> getAllUsers();

    AppUser getUserById(int id);
}
