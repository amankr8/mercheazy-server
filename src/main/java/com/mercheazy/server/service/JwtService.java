package com.mercheazy.server.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    Long getExpirationTime();

    Boolean isTokenValid(String token, UserDetails userDetails);
}
