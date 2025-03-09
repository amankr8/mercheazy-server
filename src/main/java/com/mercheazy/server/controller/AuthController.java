package com.mercheazy.server.controller;

import com.mercheazy.server.dto.user.LoginRequestDto;
import com.mercheazy.server.dto.user.SignupRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/signup")
    ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto);

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto);
}
