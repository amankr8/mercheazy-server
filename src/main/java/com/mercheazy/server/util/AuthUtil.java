package com.mercheazy.server.util;

import com.mercheazy.server.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            throw new IllegalStateException("User is not logged in.");
        }
    }
}
