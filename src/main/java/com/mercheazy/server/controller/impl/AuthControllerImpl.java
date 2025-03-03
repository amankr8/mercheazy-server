package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.AuthController;
import com.mercheazy.server.dto.AuthResponseDto;
import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import com.mercheazy.server.dto.UserResponseDto;
import com.mercheazy.server.service.AuthService;
import com.mercheazy.server.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto) {
        UserResponseDto signupUser = authService.signUp(signupRequestDto);
        return ResponseEntity.ok(new AuthResponseDto(null, signupUser, "User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
        UserResponseDto loginUser = authService.login(loginRequestDto);
        String jwtToken = jwtService.generateToken(loginUser.getUsername());
        return ResponseEntity.ok(new AuthResponseDto(jwtToken, loginUser, "Login successful!"));
    }
}
