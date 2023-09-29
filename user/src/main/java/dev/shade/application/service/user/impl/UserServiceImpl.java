package dev.shade.application.service.user.impl;

import dev.shade.application.model.user.UserUpdate;
import dev.shade.application.service.user.UserService;
import dev.shade.domain.repository.RoleRepository;
import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.RoleType;
import dev.shade.domain.user.User;
import dev.shade.shared.event.ActionType;
import dev.shade.shared.event.user.UserEvent;
import dev.shade.shared.exception.NotFoundException;
import dev.shade.shared.security.SecurityContextHelper;
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

    private final UserEventPublisher publisher;
    private final SecurityContextHelper securityContextHelper;
    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           RoleRepository roleRepository,
                           UserEventPublisher publisher,
                           SecurityContextHelper securityContextHelper,
                           Validator validator
    ) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.publisher = publisher;
        this.securityContextHelper = securityContextHelper;
        this.validator = validator;
    }

    @Override
    @Transactional
    public User createUser(@Valid @NotNull User user) {
        Role role = roleRepository.findBy(RoleType.ROLE_USER)
                                  .orElseThrow();
        User createdUser = user.initialize(role, "system");
        User savedUser = repository.save(createdUser.validate(validator));
        publisher.publish(UserEvent.builder()
                                   .actionType(ActionType.CREATION)
                                   .userId(savedUser.getId())
                                   .email(savedUser.getEmail())
                                   .build());
        return savedUser;
    }

    @Override
    public User findById(UUID userId) {
        return repository.findById(userId)
                         .orElseThrow(() -> new NotFoundException(userId, User.class));
    }

    @Override
    public Optional<User> findBy(String username) {
        return repository.findBy(username);
    }

    @Override
    @Transactional
    public void updateUser(@NotNull UUID userId, @Valid @NotNull UserUpdate user) {
        User currentUser = repository.findById(userId)
                                     .orElseThrow(() -> new NotFoundException(userId, User.class));

        User updatedUser = currentUser.update(user, securityContextHelper.username());
        repository.save(updatedUser.validate(validator));
    }
}
