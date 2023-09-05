package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.user.User;
import dev.shade.shared.security.SecurityContextHelper;
import dev.shade.shared.security.TargetedPermissionEvaluator;
import dev.shade.shared.security.model.PermissionScope;
import dev.shade.shared.security.model.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static dev.shade.shared.security.model.PermissionScope.MINE;
import static dev.shade.shared.security.model.PermissionScope.OTHER;

@Component
public class UserPermissionEvaluator implements TargetedPermissionEvaluator {

    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public UserPermissionEvaluator(SecurityContextHelper securityContextHelper) {
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
        PermissionScope scope = Optional.of(targetId.toString())
                                        .map(UUID::fromString)
                                        .map(it -> hasAccess(it, securityContextHelper.roleCode()))
                                        .map(it -> Boolean.TRUE.equals(it) ? MINE : OTHER)
                                        .orElse(OTHER);

        return securityContextHelper.resolvePermission(scope, String.valueOf(permission));
    }

    private boolean hasAccess(UUID userId, RoleCode code) {
        return switch (code) {
            case ROLE_USER -> userId.equals(securityContextHelper.userId());
            case ROLE_ADMIN -> false;
        };
    }
}
