package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.User;
import dev.shade.shared.security.SecurityContextHelper;
import dev.shade.shared.security.model.PermissionScope;
import dev.shade.shared.security.model.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static dev.shade.shared.security.model.PermissionScope.MINE;
import static dev.shade.shared.security.model.PermissionScope.OTHER;

@Component("userSecurity")
public class UserSecurityExpression {

    private final UserRepository userRepository;

    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public UserSecurityExpression(UserRepository userRepository, SecurityContextHelper securityContextHelper) {
        this.userRepository = userRepository;
        this.securityContextHelper = securityContextHelper;
    }

    public boolean authorize(UUID id, String permission) {
        Optional<User> user = userRepository.findById(id);
        PermissionScope scope = user.map(it -> hasAccess(it, securityContextHelper.roleCode()))
                                    .map(it -> Boolean.TRUE.equals(it) ? MINE : OTHER)
                                    .orElse(OTHER);
        return securityContextHelper.resolvePermission(scope, permission);
    }

    private boolean hasAccess(User user, RoleCode code) {
        return switch (code) {
            case ROLE_USER -> user.getUserName().equals(securityContextHelper.username());
            case ROLE_ADMIN -> false;
        };
    }

}
