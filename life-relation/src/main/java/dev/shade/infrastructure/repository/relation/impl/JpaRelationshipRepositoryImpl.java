package dev.shade.infrastructure.repository.relation.impl;

import dev.shade.domain.relation.Relationship;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.infrastructure.repository.relation.JpaRelationshipMapper;
import dev.shade.infrastructure.repository.relation.RelationshipJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaRelationshipRepositoryImpl implements RelationshipRepository {

    private final RelationshipJpaEntityRepository repository;
    private final JpaRelationshipMapper mapper;

    @Autowired
    public JpaRelationshipRepositoryImpl(RelationshipJpaEntityRepository repository, JpaRelationshipMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Relationship relationship) {
        repository.save(mapper.mapToEntity(relationship));
    }

    @Override
    public Optional<Relationship> findById(UUID relationshipId) {
        return repository.findById(relationshipId).map(mapper::mapToRelationship);
    }
}
