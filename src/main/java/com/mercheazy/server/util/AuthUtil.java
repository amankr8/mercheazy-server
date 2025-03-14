package com.mercheazy.server.util;

import com.mercheazy.server.entity.user.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static AuthUser getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthUser) {
            return (AuthUser) principal;
        } else {
            throw new IllegalStateException("User is not logged in.");
        }
    }
}
