package com.mercheazy.server.controller;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/signup")
    ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto);

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @GetMapping("google-login")
    ResponseEntity<?> googleLogin(@RequestParam String code);
}
