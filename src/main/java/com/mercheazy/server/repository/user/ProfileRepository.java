package com.mercheazy.server.repository.user;

import com.mercheazy.server.entity.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
