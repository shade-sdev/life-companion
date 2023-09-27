package dev.shade.application.service.user.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import dev.shade.domain.repository.RoleRepository;
import dev.shade.domain.repository.UserRepository;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.RoleType;
import dev.shade.domain.user.User;
import dev.shade.domain.user.User.Security;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    private final UUID USER_ID = UUID.randomUUID();

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserEventPublisher publisher;

    @Spy
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    public void init() {
        when(userRepository.save(any()))
                .thenReturn(User.builder().build());

        when(roleRepository.findBy(any()))
                .thenReturn(Optional.of(Role.builder()
                                            .id(UUID.randomUUID())
                                            .name(RoleType.ROLE_USER)
                                            .build()));
    }

    @Test
    void createUser() {
        User user = userService.createUser(buildUser(USER_ID));
        Mockito.verify(userRepository, times(1)).save(any());
        Mockito.verify(publisher, times(1)).publish(any());
        Assertions.assertNotNull(user);
    }

    private User buildUser(UUID userId) {
        return User.builder()
                   .id(userId)
                   .userName("userName")
                   .email("email@email.com")
                   .role(Role.builder()
                             .id(UUID.randomUUID())
                             .name(RoleType.ROLE_USER)
                             .permissions(List.of())
                             .build())
                   .security(Security.builder()
                                     .isAccountNonLocked(true)
                                     .isTwoFactorEnabled(false)
                                     .password("password")
                                     .build())
                   .build();
    }
}
