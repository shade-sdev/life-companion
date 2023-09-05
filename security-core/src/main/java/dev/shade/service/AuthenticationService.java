package dev.shade.service;

import dev.shade.domain.user.User;
import dev.shade.model.UserAuthenticatedResponseApiBean;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.tuple.Pair;

public interface AuthenticationService {
    Pair<User, String> createUser(@NotNull @Valid User user);

    UserAuthenticatedResponseApiBean authenticateUser(String username, String password);

    UserAuthenticatedResponseApiBean refreshUserToken(String authorization);
}
