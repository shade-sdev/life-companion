package dev.shade.shared.security;

import dev.shade.shared.security.model.PermissionScope;
import dev.shade.shared.security.model.RoleCode;
import dev.shade.shared.security.model.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SecurityContextHelper extends SecurityPermissionResolver {

    public UUID userId() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                       .map(SecurityContext::getAuthentication)
                       .map(Authentication::getPrincipal)
                       .filter(UserPrincipal.class::isInstance)
                       .map(it -> ((UserPrincipal) it).getId())
                       .orElseThrow();
    }


    public String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Set<String> authorities() {
        return SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toSet());
    }

    public RoleCode roleCode() {
        Object userPrincipal = SecurityContextHolder.getContext()
                                                    .getAuthentication()
                                                    .getPrincipal();

        if (userPrincipal instanceof UserPrincipal principal) {
            return principal.getRole().getRoleCode();
        }
        return RoleCode.ROLE_USER;
    }

    public boolean resolvePermission(PermissionScope scope, String permission) {
        return resolvePermission(scope, permission, authorities());
    }

    public boolean hasRole(RoleCode code) {
        return authorities()
                .stream()
                .anyMatch(it -> code.getCode().equals(it));
    }

}