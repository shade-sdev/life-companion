package dev.shade.infrastructure.repository.authority.impl;

import dev.shade.domain.repository.RoleRepository;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.RoleType;
import dev.shade.infrastructure.repository.authority.RoleJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRoleRepositoryImpl implements RoleRepository {

    private final RoleJpaEntityRepository repository;

    @Autowired
    public JpaRoleRepositoryImpl(RoleJpaEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findBy(RoleType roleType) {
        return repository.findByName(roleType)
                         .map(it -> Role.builder()
                                        .id(it.getId())
                                        .name(it.getName())
                                        .build());
    }

}
