package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.user.LoginRequestDto;
import com.mercheazy.server.dto.user.SignupRequestDto;
import com.mercheazy.server.dto.user.UserResponseDto;
import com.mercheazy.server.entity.User;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.UserRepository;
import com.mercheazy.server.service.CartService;
import com.mercheazy.server.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mercheazy.server.entity.User.Role.ADMIN;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CartService cartService;

    @Value("${spring.security.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        User admin = new User();
        admin.setUsername("mercheazy");
        admin.setFirstName("MerchEazy");
        admin.setEmail("hello@mercheazy.com");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(ADMIN);
        userRepository.save(admin);
        System.out.println("MerchEazy admin created!");
    }

    @Override
    public UserResponseDto signUp(SignupRequestDto signupRequestDto) {
        // Check if the user already exists by email
        if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user is already registered with this email");
        }

        User user = signupRequestDto.toUser();
        user.setUsername(generateUniqueUsername(user.getUsername()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        cartService.createUserCart(user);

        return savedUser.toUserResponseDto();
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

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(User::toUserResponseDto).toList();
    }

    @Override
    public UserResponseDto getUserDetailsById(int id) {
        return userRepository.findById(id).map(User::toUserResponseDto).orElse(null);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }
}
