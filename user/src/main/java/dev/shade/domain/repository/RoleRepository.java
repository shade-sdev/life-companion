package dev.shade.domain.repository;

import dev.shade.domain.user.Role;
import dev.shade.domain.user.RoleType;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findBy(RoleType roleType);
}
