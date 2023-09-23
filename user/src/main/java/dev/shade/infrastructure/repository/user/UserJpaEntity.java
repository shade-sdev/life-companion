package dev.shade.infrastructure.repository.user;

import dev.shade.infrastructure.repository.Auditable;
import dev.shade.infrastructure.repository.authority.RoleJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class UserJpaEntity extends Auditable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @Embedded
    private Security security;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Getter
    public static class Security implements Serializable {

        @Column(name = "password")
        private String password;

        @Column(name = "is_account_non_locked")
        private boolean isAccountNonLocked;

        @Column(name = "is_two_factor_enabled")
        private boolean isTwoFactorEnabled;

        @Column(name = "two_factor_secret_key")
        private String twoFactorSecretKey;

    }

}
