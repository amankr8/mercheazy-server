package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.AuthController;
import com.mercheazy.server.dto.auth.AuthResponseDto;
import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.dto.user.UserResponseDto;
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
        UserResponseDto signupUser = authService.signUp(signupRequestDto).toUserResponseDto();
        return ResponseEntity.ok(new AuthResponseDto(null, signupUser, "User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
        UserResponseDto loginUser = authService.login(loginRequestDto).toUserResponseDto();
        String jwtToken = jwtService.generateToken(loginUser.getUsername());
        return ResponseEntity.ok(new AuthResponseDto(jwtToken, loginUser, "Login successful!"));
    }

    @Override
    public ResponseEntity<?> googleLogin(String code) {
        UserResponseDto oAuthUser = authService.googleLogin(code).toUserResponseDto();
        String jwtToken = jwtService.generateToken(oAuthUser.getUsername());
        return ResponseEntity.ok(new AuthResponseDto(jwtToken, oAuthUser, "Login successful!"));
    }
}
