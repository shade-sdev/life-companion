package dev.shade.infrastructure.repository.relation;

import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.RelationshipStatus;
import dev.shade.infrastructure.repository.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "relationship", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class RelationshipJpaEntity extends Auditable implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "requester_person_id")
    private UUID requesterPersonId;

    @Column(name = "receiver_person_id")
    private UUID receiverPersonId;

    @Column(name = "relation_type")
    @Enumerated(EnumType.STRING)
    private RelationType relationType;

    @Column(name = "relationship_status")
    @Enumerated(EnumType.STRING)
    private RelationshipStatus status;

}
