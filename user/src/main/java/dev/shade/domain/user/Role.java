package dev.shade.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static lombok.Builder.Default;

@Value
@Builder(toBuilder = true)
public class Role implements Serializable {

    @NotNull
    UUID id;

    @Default
    RoleType name = RoleType.ROLE_USER;

    List<Permission> permissions;

}
