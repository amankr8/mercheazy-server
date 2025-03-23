package com.mercheazy.server.controller.impl;

import com.mercheazy.server.controller.UserController;
import com.mercheazy.server.dto.user.UserResponseDto;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.entity.user.Profile;
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
        List<AuthUser> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users.stream().map(AuthUser::toUserResponseDto).toList());
    }

    @Override
    public ResponseEntity<?> getUserById(int id) {
        AuthUser user = userService.getUserById(id);
        return ResponseEntity.ok().body(user.toUserResponseDto());
    }

    @Override
    public ResponseEntity<?> getLoggedInUser() {
        AuthUser loggedInUser = userService.getUserById(AuthUtil.getLoggedInUser().getId());
        return ResponseEntity.ok().body(loggedInUser.toUserResponseDto());
    }

    @Override
    public ResponseEntity<?> getUserProfiles() {
        List<Profile> profiles = userService.getProfilesByUserId(AuthUtil.getLoggedInUser().getId());
        return ResponseEntity.ok().body(profiles);
    }
}
