package dev.shade.service.user;

import dev.shade.domain.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface UserService {
    User createUser(@Valid @NotNull User user);

    void updateUser(@NotNull UUID userId, @Valid @NotNull User user);
}
