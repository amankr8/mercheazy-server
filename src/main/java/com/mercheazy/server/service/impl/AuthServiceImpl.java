package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.auth.LoginRequestDto;
import com.mercheazy.server.dto.auth.SignupRequestDto;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.entity.user.Profile;
import com.mercheazy.server.repository.user.UserRepository;
import com.mercheazy.server.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements com.mercheazy.server.service.AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${spring.security.username}")
    private String adminUsername;

    @Value("${spring.security.password}")
    private String adminPassword;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${frontend.url}")
    private String frontendUrl;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            AuthUser admin = AuthUser.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email("hello@mercheazy.com")
                    .profiles(new ArrayList<>())
                    .role(AuthUser.Role.ADMIN)
                    .build();

            Profile adminProfile = Profile.builder()
                    .primary(true)
                    .name("MerchEazy")
                    .build();
            userService.addUserProfile(admin, adminProfile);
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
                .profiles(new ArrayList<>())
                .role(AuthUser.Role.USER)
                .build();

        Profile defaultProfile = Profile.builder()
                .primary(true)
                .build();

        return userService.addUserProfile(authUser, defaultProfile);
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
        // Manually exchange authorization code for access token
        String accessToken = exchangeAuthorizationCodeForToken(code);

        // Fetch user details from Google API
        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, Map.class);
        Map<?, ?> userAttributes = (Map<?, ?>) response.getBody();

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
                            .profiles(new ArrayList<>())
                            .role(AuthUser.Role.USER)
                            .build();

                    Profile defaultProfile = Profile.builder()
                            .name(name)
                            .primary(true)
                            .build();

                    return userService.addUserProfile(newUser, defaultProfile);
                });
    }

    private String exchangeAuthorizationCodeForToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("code", authorizationCode);
        body.add("redirect_uri", frontendUrl + "/oauth2/callback");
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<?> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", requestEntity, Map.class);

        Map<?,?> responseBody = (Map<?, ?>) response.getBody();
        if (responseBody != null && responseBody.containsKey("access_token")) {
            return (String) responseBody.get("access_token");
        } else {
            throw new RuntimeException("Failed to retrieve access token");
        }
    }
}
