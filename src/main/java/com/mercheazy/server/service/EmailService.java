package com.mercheazy.server.service;

public interface EmailService {
    void sendUserVerificationMail(String email, String token);
}
