package com.mercheazy.server.repository.user;

import com.mercheazy.server.entity.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
    Optional<UserToken> findByToken(String token);
}
