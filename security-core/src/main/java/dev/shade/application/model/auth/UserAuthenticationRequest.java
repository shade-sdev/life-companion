package dev.shade.application.model.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserAuthenticationRequest {

    @NotNull
    String username;

    @NotNull
    String password;

    @Size(min = 6, max = 6)
    String code;
}
