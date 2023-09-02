package dev.shade.infrastructure.repository.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "permission")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PermissionJpaEntity implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;
}
