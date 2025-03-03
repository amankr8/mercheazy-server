package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.LoginRequestDto;
import com.mercheazy.server.dto.SignupRequestDto;
import com.mercheazy.server.dto.UserResponseDto;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.AuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${spring.security.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        User defaultUser = new User();
        defaultUser.setUsername("mercheazy");
        defaultUser.setFirstName("MerchEazy");
        defaultUser.setEmail("hello@mercheazy.com");
        defaultUser.setPassword(passwordEncoder.encode(adminPassword));
        defaultUser.setRole(User.Role.ADMIN);
        userRepository.save(defaultUser);
        System.out.println("MerchEazy admin created!");
    }

    @Override
    public UserResponseDto signUp(SignupRequestDto signupRequestDto) {
        // Check if the user already exists by email
        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user is already registered with this email");
        }

        User user = signupRequestDto.toUser();
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setUsername(generateUniqueUsername((user.getFirstName() + user.getLastName()).toLowerCase()));

        return userRepository.save(user).toUserResponseDto();
    }

    private String generateUniqueUsername(String username) {
        // Generate a unique username
        String uniqueUsername = username;
        int i = 1;
        while (userRepository.findByUsername(uniqueUsername).isPresent()) {
            uniqueUsername = username + i;
            i++;
        }
        return uniqueUsername;
    }

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Email does not exist in database"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        return user.toUserResponseDto();
    }
}
