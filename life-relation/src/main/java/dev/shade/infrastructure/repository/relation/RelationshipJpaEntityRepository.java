package dev.shade.infrastructure.repository.relation;

import dev.shade.domain.relation.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RelationshipJpaEntityRepository extends JpaRepository<RelationshipJpaEntity, UUID>, QuerydslPredicateExecutor<RelationshipJpaEntity> {

    @Query("SELECT r.relationType FROM RelationshipJpaEntity r" +
            " WHERE ((r.requesterPersonId = :requesterPersonId AND r.receiverPersonId = :targetedPersonId) OR" +
            "(r.requesterPersonId = :targetedPersonId AND r.receiverPersonId = :requesterPersonId))" +
            " AND r.status = 'ACTIVE'")
    Optional<RelationType> getRelationType(UUID requesterPersonId, UUID targetedPersonId);

}
