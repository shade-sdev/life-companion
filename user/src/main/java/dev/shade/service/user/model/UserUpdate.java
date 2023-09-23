package dev.shade.service.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserUpdate {

    @NotNull String email;
}
