package dev.shade.shared.security;

import org.springframework.security.access.PermissionEvaluator;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {
    String getTargetType();
}
