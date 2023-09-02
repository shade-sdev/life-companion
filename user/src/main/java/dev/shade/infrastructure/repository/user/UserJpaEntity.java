package dev.shade.infrastructure.repository.user;

import dev.shade.infrastructure.repository.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @Column(name = "password")
    private String password;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

}
