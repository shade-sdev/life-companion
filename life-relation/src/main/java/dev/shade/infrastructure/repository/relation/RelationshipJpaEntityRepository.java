package dev.shade.infrastructure.repository.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RelationshipJpaEntityRepository extends JpaRepository<RelationshipJpaEntity, UUID> {
}
