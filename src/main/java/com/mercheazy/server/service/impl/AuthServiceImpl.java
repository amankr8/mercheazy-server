package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.CartService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mercheazy.server.entity.user.AuthUser.Role.ADMIN;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements com.mercheazy.server.service.AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;

    @Value("${spring.security.username}")
    private String adminUsername;

    @Value("${spring.security.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            AuthUser admin = AuthUser.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email("hello@mercheazy.com")
                    .role(ADMIN)
                    .firstName("MerchEazy")
                    .build();
            admin = userRepository.save(admin);
            cartService.createUserCart(admin);
            System.out.println("MerchEazy admin created!");
        }
    }

    @Override
    public AuthUser signUp(SignupRequestDto signupRequestDto) {
        // Check if the user already exists by email
        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user is already registered with this email");
        }

        AuthUser authUser = signupRequestDto.toUser();
        authUser.setUsername(generateUniqueUsername(authUser.getUsername()));
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));

        AuthUser savedAuthUser = userRepository.save(authUser);
        cartService.createUserCart(savedAuthUser);

        return savedAuthUser;
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
    public AuthUser login(LoginRequestDto loginRequestDto) {
        AuthUser authUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Email does not exist in database"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUser.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        return authUser;
    }
}
