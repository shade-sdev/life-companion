package dev.shade.service.user;

import dev.shade.domain.user.User;
import dev.shade.service.user.model.UserUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(@Valid @NotNull User user);

    Optional<User> findById(@NotNull UUID userId);

    Optional<User> findBy(@NotNull String username);

    void updateUser(@NotNull UUID userId, @Valid @NotNull UserUpdate user);
}
