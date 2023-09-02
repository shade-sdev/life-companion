package dev.shade.repository;

import dev.shade.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UUID id);

}
