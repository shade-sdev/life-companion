package dev.shade.infrastructure.repository.user;

import dev.shade.domain.user.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "role", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RoleEntity {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleType name;
}
