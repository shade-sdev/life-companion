package dev.shade.service;

import dev.shade.domain.user.User;
import dev.shade.model.UserAuthenticatedResponseApiBean;

public interface AuthenticationService {
    User createUser(User user);

    UserAuthenticatedResponseApiBean authenticateUser(String username, String password);
}
