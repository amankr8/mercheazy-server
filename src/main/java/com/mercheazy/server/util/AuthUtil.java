package com.mercheazy.server.util;

import com.mercheazy.server.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static AppUser getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUser) {
            return (AppUser) principal;
        } else {
            throw new IllegalStateException("User is not logged in.");
        }
    }
}
