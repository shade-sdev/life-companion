package dev.shade.domain.user;

import dev.shade.domain.user.validation.ValidSecretKey;
import dev.shade.service.user.model.UserUpdate;
import dev.shade.shared.domain.Auditable;
import dev.shade.shared.domain.DomainValidator;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class User extends DomainValidator<User> implements Serializable {

    @Default
    @NotNull
    UUID id = UUID.randomUUID();

    @NotNull
    String userName;

    @NotNull
    @Email
    String email;

    @Default
    @NotNull
    Role role = Role.builder().build();

    @Default
    @NotNull
    Auditable audit = Auditable.builder().build();

    @Valid
    @NotNull
    @Default
    Security security = Security.builder().build();

    @Value
    @Builder(toBuilder = true)
    public static class Security implements Serializable {

        @NotNull
        String password;

        @NotNull
        @Default
        boolean isAccountNonLocked = true;

        @NotNull
        @Default
        boolean isTwoFactorEnabled = false;

        @NotNull(groups = ValidSecretKey.class)
        String twoFactorSecretKey;

    }

    public User validate(Validator validator) {
        List<Class<?>> groups = new ArrayList<>();
        if (this.getSecurity().isTwoFactorEnabled) {
            groups.add(ValidSecretKey.class);
        }
        this.validate(this, validator, groups.toArray(new Class[0]));
        return this;
    }

    public User initialize(Role role, String createdBy) {
        return this.toBuilder()
                   .role(role)
                   .audit(Auditable.builder()
                                   .createdBy(createdBy)
                                   .build())
                   .build();
    }

    public User initializeSecurity(String password, String secretKey) {
        UserBuilder userBuilder = this.toBuilder();
        User.Security.SecurityBuilder securityBuilder = this.getSecurity()
                                                            .toBuilder()
                                                            .password(password);

        if (this.getSecurity().isTwoFactorEnabled) {
            securityBuilder.twoFactorSecretKey(secretKey);
        }

        return userBuilder.security(securityBuilder.build())
                          .build();
    }

    public User update(UserUpdate updatedUser, String updatedBy) {
        return this.toBuilder()
                   .email(updatedUser.getEmail())
                   .audit(this.getAudit().toBuilder()
                              .lastModifiedBy(updatedBy)
                              .build())
                   .build();
    }

}
