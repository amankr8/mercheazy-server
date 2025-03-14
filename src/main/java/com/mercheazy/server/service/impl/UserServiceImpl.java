package com.mercheazy.server.service.impl;

import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<AuthUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AuthUser getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
