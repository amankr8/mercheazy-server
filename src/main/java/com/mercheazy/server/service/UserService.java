package com.mercheazy.server.service;

import com.mercheazy.server.dto.user.ProfileRequestDto;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.entity.user.Profile;

import java.util.List;

public interface UserService {
    List<AuthUser> getAllUsers();

    AuthUser getUserById(int id);

    List<Profile> getProfilesByUserId(int userId);

    AuthUser addUserProfile(AuthUser authUser, Profile profile);

    Profile addProfile(ProfileRequestDto profileRequestDto);
}
