package dev.shade.domain.user;

import dev.shade.domain.Auditable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class User {

    @Default
    @NotNull
    UUID id = UUID.randomUUID();

    @NotNull
    String userName;

    String firstName;

    String lastName;

    @NotNull
    @Email
    String email;

    @Default
    @NotNull
    Role role = Role.builder().build();

    @Default
    @NotNull
    Auditable audit = Auditable.builder().build();

    @NotNull
    @Default
    boolean isAccountNonLocked = true;

    public User initialize(Role role, String createdBy) {
        return this.toBuilder()
                   .role(role)
                   .audit(Auditable.builder()
                                   .createdBy(createdBy)
                                   .build())
                   .build();
    }

    public User update(User updatedUser, String updatedBy) {
        return this.toBuilder()
                   .firstName(updatedUser.getFirstName())
                   .lastName(updatedUser.getLastName())
                   .email(updatedUser.getEmail())
                   .audit(this.getAudit().toBuilder()
                              .lastModifiedBy(updatedBy)
                              .build())
                   .build();
    }
}
