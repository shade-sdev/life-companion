package dev.shade.domain.repository;

import dev.shade.domain.relation.Relationship;

import java.util.Optional;
import java.util.UUID;

public interface RelationshipRepository {

    void save(Relationship relationship);

    Optional<Relationship> findById(UUID relationshipId);
}
