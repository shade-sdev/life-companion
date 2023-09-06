package dev.shade.service;

import dev.shade.domain.user.User;
import dev.shade.model.UserAuthenticatedResponseApiBean;
import dev.shade.service.model.UserAuthenticationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.tuple.Pair;

public interface AuthenticationService {
    Pair<User, String> createUser(@NotNull @Valid User user);

    UserAuthenticatedResponseApiBean authenticateUser(@NotNull @Valid UserAuthenticationRequest request);

    UserAuthenticatedResponseApiBean refreshUserToken(String authorization);
}
