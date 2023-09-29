package dev.shade.domain.repository;

import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;

import java.util.Optional;
import java.util.UUID;

public interface RelationshipRepository {

    Optional<RelationType> getRelationType(UUID requesterPersonId, UUID targetedPersonId);

    Relationship save(Relationship relationship);

    Optional<Relationship> findById(UUID relationshipId);
}
