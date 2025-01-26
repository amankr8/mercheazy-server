package com.mercheazy.server.controller;

import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/signup")
    ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto);

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto);
}
