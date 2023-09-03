package dev.shade.infrastructure.repository.authority;

import dev.shade.domain.user.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RoleJpaEntity implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "permission_id", insertable = false, updatable = false))
    @Fetch(FetchMode.SUBSELECT)
    private List<PermissionJpaEntity> permissions;
}
