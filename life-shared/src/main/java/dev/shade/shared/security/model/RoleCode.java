package dev.shade.shared.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleCode {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String code;
}
