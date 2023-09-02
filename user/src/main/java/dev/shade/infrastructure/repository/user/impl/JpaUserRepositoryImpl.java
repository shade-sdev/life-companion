package dev.shade.infrastructure.repository.user.impl;

import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.User;
import dev.shade.infrastructure.repository.user.JpaUserMapper;
import dev.shade.infrastructure.repository.user.UserJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepositoryImpl implements UserRepository {

    private final UserJpaEntityRepository repository;
    private final JpaUserMapper mapper;

    @Autowired
    public JpaUserRepositoryImpl(UserJpaEntityRepository repository, JpaUserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return Optional.of(user)
                       .map(mapper::mapToEntity)
                       .map(repository::save)
                       .map(mapper::mapToUser)
                       .orElse(User.builder().build());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id)
                         .map(mapper::mapToUser);
    }
}
