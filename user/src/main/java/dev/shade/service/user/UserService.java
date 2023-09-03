package dev.shade.service.user;

import dev.shade.domain.user.User;
import dev.shade.service.user.model.UserUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(@Valid @NotNull User user);

    @PreAuthorize("hasPermission(#userId, 'dev.shade.domain.user.User', 'User#R')")
    User findById(@NotNull UUID userId);

    @PreAuthorize("hasPermission(#userId, 'dev.shade.domain.user.User', 'User#U')")
    void updateUser(@NotNull UUID userId, @Valid @NotNull UserUpdate user);

    // Internal

    Optional<User> findBy(@NotNull String username);

}
