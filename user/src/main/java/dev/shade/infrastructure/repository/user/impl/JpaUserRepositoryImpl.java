package dev.shade.infrastructure.repository.user.impl;

import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.User;
import dev.shade.infrastructure.repository.user.JpaUserMapper;
import dev.shade.infrastructure.repository.user.UserJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Caching(evict = {
            @CacheEvict(value = "users", key = "#user.id"),
            @CacheEvict(value = "users", key = "#user.userName"),
    })
    public User save(User user) {
        return Optional.of(user)
                       .map(mapper::mapToEntity)
                       .map(repository::save)
                       .map(mapper::mapToUser)
                       .orElse(User.builder().build());
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public Optional<User> findById(UUID id) {
        return repository.findById(id)
                         .map(mapper::mapToUser);
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public Optional<User> findBy(String username) {
        return repository.findByUserName(username)
                         .map(mapper::mapToUser);
    }
}
