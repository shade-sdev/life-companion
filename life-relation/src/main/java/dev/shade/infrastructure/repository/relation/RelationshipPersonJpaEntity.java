package dev.shade.infrastructure.repository.relation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "person", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RelationshipPersonJpaEntity implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;
}
