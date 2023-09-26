package dev.shade.application.service;

import dev.shade.application.model.auth.UserAuthenticatedResponseApiBean;
import dev.shade.application.model.auth.UserAuthenticationRequest;
import dev.shade.domain.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.tuple.Pair;

public interface AuthenticationService {
    Pair<User, String> createUser(@NotNull @Valid User user);

    UserAuthenticatedResponseApiBean authenticateUser(@NotNull @Valid UserAuthenticationRequest request);

    UserAuthenticatedResponseApiBean refreshUserToken(String authorization);
}
