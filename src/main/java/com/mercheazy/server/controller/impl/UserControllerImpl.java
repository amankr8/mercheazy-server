package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.UserController;
import com.mercheazy.server.dto.user.UserResponseDto;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers().stream()
                .map(AuthUser::toUserResponseDto).toList();
        return ResponseEntity.ok().body(users);
    }

    @Override
    public ResponseEntity<?> getUserProfiles() {
        List<Pro> profiles = userService.getProfilesByUserId(AuthUtil.getLoggedInUser().getId()).stream()
                .map(AuthUser::toUserResponseDto).toList();
        return ResponseEntity.ok().body(profiles);
    }
}
