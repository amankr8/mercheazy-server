package com.mercheazy.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/api/users")
public interface UserController {
    @GetMapping
    ResponseEntity<?> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<?> getUserById(int id);

    @GetMapping("/current")
    ResponseEntity<?> getLoggedInUser();

    @GetMapping("/profiles")
    ResponseEntity<?> getUserProfiles();

    @DeleteMapping("/current")
    ResponseEntity<?> deleteLoggedInUser();
}
