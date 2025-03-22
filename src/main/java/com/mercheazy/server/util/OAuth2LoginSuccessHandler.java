package com.mercheazy.server.util;

import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.repository.user.UserRepository;
import com.mercheazy.server.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${spring.frontend.url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        AuthUser authUser = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    AuthUser newUser = AuthUser.builder()
                            .email(email)
                            .role(AuthUser.Role.USER)
                            .profiles(new ArrayList<>())
                            .build();
                    return userRepository.save(newUser);
                });

        String token = jwtService.generateToken(authUser.getUsername());
        response.sendRedirect(redirectUrl + "/oauth2/redirect?token=" + token);
    }
}
