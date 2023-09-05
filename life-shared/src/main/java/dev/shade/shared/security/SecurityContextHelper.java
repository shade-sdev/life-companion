package dev.shade.shared.security;

import dev.shade.shared.security.model.PermissionScope;
import dev.shade.shared.security.model.RoleCode;

import java.util.Set;
import java.util.UUID;

public interface SecurityContextHelper {

    UUID userId();

    String username();

    Set<String> authorities();

    RoleCode roleCode();

    boolean resolvePermission(PermissionScope scope, String permission);

    boolean hasRole(RoleCode code);
}
