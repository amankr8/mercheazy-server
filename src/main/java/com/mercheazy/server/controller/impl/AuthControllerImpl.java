package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.AuthController;
import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;

public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<?> registerUser(SignupRequestDto signupRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequestDto loginRequestDto) {
        return null;
    }
}
