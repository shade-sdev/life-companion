package dev.shade.shared.security;

import dev.shade.shared.security.model.PermissionScope;
import dev.shade.shared.security.model.RoleCode;

import java.util.Set;

public interface SecurityContextHelper {

    String username();

    Set<String> authorities();

    RoleCode roleCode();

    boolean resolvePermission(PermissionScope scope, String permission);

    boolean hasRole(RoleCode code);
}
