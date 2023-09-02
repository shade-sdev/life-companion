package dev.shade.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

import static lombok.Builder.Default;

@Value
@Builder(toBuilder = true)
public class Role {

    @NotNull
    UUID id;

    @Default
    RoleType name = RoleType.ROLE_USER;
}
