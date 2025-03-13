package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.AuthController;
import com.mercheazy.server.dto.user.AuthResponseDto;
import com.mercheazy.server.dto.user.LoginRequestDto;
import com.mercheazy.server.dto.user.SignupRequestDto;
import com.mercheazy.server.dto.user.UserResponseDto;
import com.mercheazy.server.service.JwtService;
import com.mercheazy.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto) {
        UserResponseDto signupUser = userService.signUp(signupRequestDto).toUserResponseDto();
        return ResponseEntity.ok(new AuthResponseDto(null, signupUser, "User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
        UserResponseDto loginUser = userService.login(loginRequestDto).toUserResponseDto();
        String jwtToken = jwtService.generateToken(loginUser.getUsername());
        return ResponseEntity.ok(new AuthResponseDto(jwtToken, loginUser, "Login successful!"));
    }
}
