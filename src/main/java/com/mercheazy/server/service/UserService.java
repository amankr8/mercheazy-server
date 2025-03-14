package com.mercheazy.server.service;

import com.mercheazy.server.entity.user.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> getAllUsers();

    AppUser getUserById(int id);
}
