package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.entity.user.Profile;
import com.mercheazy.server.repository.user.UserRepository;
import com.mercheazy.server.service.CartService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements com.mercheazy.server.service.AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate;
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
                    .role(AuthUser.Role.ADMIN)
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

        AuthUser authUser = AuthUser.builder()
                .username(signupRequestDto.getEmail())
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .role(AuthUser.Role.USER)
                .profiles(new ArrayList<>())
                .build();

        AuthUser savedAuthUser = userRepository.save(authUser);
        Profile defaultProfile = Profile.builder()
                .primary(true)
                .authUser(savedAuthUser)
                .build();

        savedAuthUser.getProfiles().add(defaultProfile);
        userRepository.save(savedAuthUser);
        cartService.createUserCart(savedAuthUser);

        return savedAuthUser;
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

    @Override
    public AuthUser googleLogin(String code) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("google", code);

        if (authorizedClient == null) {
            throw new AuthenticationCredentialsNotFoundException("Error processing request");
        }

        // Get access token
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // Fetch user details from Google API
        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, Map.class);
        Map<?,?> userAttributes = (Map<?, ?>) response.getBody();

        if (userAttributes == null || !userAttributes.containsKey("email")) {
            throw new AuthenticationCredentialsNotFoundException("Error processing request");
        }

        String email = (String) userAttributes.get("email");
        String name = (String) userAttributes.get("name");
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    AuthUser newUser = AuthUser.builder()
                            .username(email)
                            .email(email)
                            .role(AuthUser.Role.USER)
                            .profiles(new ArrayList<>())
                            .build();
                    newUser = userRepository.save(newUser);

                    Profile defaultProfile = Profile.builder()
                            .name(name)
                            .authUser(newUser)
                            .primary(true)
                            .build();
                    newUser.getProfiles().add(defaultProfile);
                    return userRepository.save(newUser);
                });
    }
}
