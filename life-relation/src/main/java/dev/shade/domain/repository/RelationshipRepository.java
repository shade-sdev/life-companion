package dev.shade.domain.repository;

import dev.shade.application.model.relationship.RelationshipSearchCriteria;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface RelationshipRepository {

    Page<Relationship> search(RelationshipSearchCriteria searchCriteria, Integer pageNumber, Integer pageSize);

    Optional<RelationType> getRelationType(UUID requesterPersonId, UUID targetedPersonId);

    Relationship save(Relationship relationship);

    Optional<Relationship> findById(UUID relationshipId);
}
