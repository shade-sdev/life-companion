package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.User;
import dev.shade.shared.security.SecurityContextHelper;
import dev.shade.shared.security.permissionevalutor.TargetedPermissionEvaluator;
import dev.shade.shared.security.permissionevalutor.model.PermissionScope;
import dev.shade.shared.security.permissionevalutor.model.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static dev.shade.shared.security.permissionevalutor.model.PermissionScope.MINE;
import static dev.shade.shared.security.permissionevalutor.model.PermissionScope.OTHER;

@Component
public class UserPermissionEvaluator implements TargetedPermissionEvaluator {

    private final UserRepository userRepository;

    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public UserPermissionEvaluator(UserRepository userRepository, SecurityContextHelper securityContextHelper) {
        this.userRepository = userRepository;
        this.securityContextHelper = securityContextHelper;
    }

    @Override
    public String getTargetType() {
        return User.class.getName();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Optional<User> user = userRepository.findById(UUID.fromString(targetId.toString()));
        PermissionScope scope = user.map(it -> hasAccess(it, securityContextHelper.roleCode()))
                                    .map(it -> Boolean.TRUE.equals(it) ? MINE : OTHER)
                                    .orElse(OTHER);
        return securityContextHelper.resolvePermission(scope, String.valueOf(permission));
    }

    private boolean hasAccess(User user, RoleCode code) {
        return switch (code) {
            case ROLE_USER -> user.getUserName().equals(securityContextHelper.username());
            case ROLE_ADMIN -> false;
        };
    }
}
