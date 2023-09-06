package dev.shade.service.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserAuthenticationRequest {

    @NotNull
    String username;

    @NotNull
    String password;

    @Min(6)
    @Max(6)
    Integer code;
}
