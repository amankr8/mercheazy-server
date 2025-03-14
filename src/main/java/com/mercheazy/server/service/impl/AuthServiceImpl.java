package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.entity.user.AppUser;
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

import static com.mercheazy.server.entity.user.AppUser.Role.ADMIN;

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
            AppUser admin = AppUser.builder()
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
    public AppUser signUp(SignupRequestDto signupRequestDto) {
        // Check if the user already exists by email
        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user is already registered with this email");
        }

        AppUser appUser = signupRequestDto.toUser();
        appUser.setUsername(generateUniqueUsername(appUser.getUsername()));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        AppUser savedAppUser = userRepository.save(appUser);
        cartService.createUserCart(savedAppUser);

        return savedAppUser;
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
    public AppUser login(LoginRequestDto loginRequestDto) {
        AppUser appUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Email does not exist in database"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        appUser.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        return appUser;
    }
}
