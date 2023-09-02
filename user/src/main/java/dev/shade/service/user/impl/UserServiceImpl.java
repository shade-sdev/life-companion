package dev.shade.service.user.impl;

import dev.shade.domain.repository.RoleRepository;
import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.RoleType;
import dev.shade.domain.user.User;
import dev.shade.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository, Validator validator) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public User createUser(@Valid @NotNull User user) {
        Role role = roleRepository.findBy(RoleType.ROLE_USER)
                                  .orElseThrow();
        return repository.save(user.initialize(role, "Shade"));
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public Optional<User> findBy(String username) {
        return repository.findBy(username);
    }

    @Override
    @Transactional
    public void updateUser(@NotNull UUID userId, @Valid @NotNull User user) {
        User currentUser = repository.findById(userId)
                                     .orElseThrow(() -> new IllegalArgumentException("User not found"));

        User updatedUser = currentUser.update(user, "Shade");
        validator.validate(updatedUser);
        repository.save(updatedUser);
    }
}
