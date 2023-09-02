package dev.shade.service.user.impl;

import dev.shade.domain.user.User;
import dev.shade.repository.UserRepository;
import dev.shade.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public User createUser(@Valid @NotNull User user) {
        return repository.save(user.initialize("Shade"));
    }

    @Override
    @Transactional
    public void updateUser(@NotNull UUID userId, @Valid @NotNull User user) {
        User currentUser = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User updatedUser = currentUser.update("Shade", user);
        validator.validate(updatedUser);
        repository.save(updatedUser);
    }
}
