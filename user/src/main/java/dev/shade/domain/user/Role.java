package dev.shade.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

import static lombok.Builder.Default;

@Value
@Builder(toBuilder = true)
public class Role {

    private static UUID USER_ROLE_ID = UUID.fromString("6e27aa40-ae4e-42c0-81a4-869560b59968");

    @Default
    @NotNull
    UUID id = USER_ROLE_ID;

    @Default
    RoleType name = RoleType.ROLE_USER;
}
