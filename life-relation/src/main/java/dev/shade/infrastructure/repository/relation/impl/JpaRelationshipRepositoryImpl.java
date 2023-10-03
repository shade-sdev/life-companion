package dev.shade.infrastructure.repository.relation.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import dev.shade.application.model.relationship.RelationshipSearchCriteria;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.domain.relation.RelationshipStatus;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.infrastructure.repository.relation.JpaRelationshipMapper;
import dev.shade.infrastructure.repository.relation.QRelationshipJpaEntity;
import dev.shade.infrastructure.repository.relation.RelationshipJpaEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.*;

import static dev.shade.infrastructure.repository.relation.QRelationshipJpaEntity.relationshipJpaEntity;

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
    public Page<Relationship> search(RelationshipSearchCriteria searchCriteria, Integer pageNumber, Integer pageSize) {
        return mapper.mapToPageRelationship(repository.findAll(toPredicate(searchCriteria),
                PageRequest.of(pageNumber, pageSize)));
    }

    @Override
    public Optional<RelationType> getRelationType(UUID requesterPersonId, UUID targetedPersonId) {
        return repository.getRelationType(requesterPersonId, targetedPersonId);
    }

    @Override
    public Relationship save(Relationship relationship) {
        return mapper.mapToRelationship(repository.save(mapper.mapToEntity(relationship)));
    }

    @Override
    public Optional<Relationship> findById(UUID relationshipId) {
        return repository.findById(relationshipId).map(mapper::mapToRelationship);
    }

    private Predicate toPredicate(RelationshipSearchCriteria criteria) {
        var relationship = relationshipJpaEntity;
        BooleanExpression expression = Expressions.TRUE.isTrue();

        expression = expression.and(relationship.receiverPersonId.eq(criteria.getPersonId())
                                                                 .or(relationship.requesterPersonId.eq(criteria.getPersonId())));

        if (Objects.nonNull(criteria.getAuthenticatedPersonId()) && Objects.nonNull(criteria.getTargetRelationType())) {
            expression = expression.and(Expressions.anyOf(
                    mandatoryRelationshipPredicate(relationship, criteria.getAuthenticatedPersonId(), criteria.getPersonId()),
                    visibleRelationshipPredicate(relationship, criteria.getPersonId(), criteria.getTargetRelationType())
            ));
        }

        if (StringUtils.isNotBlank(criteria.getFirstName())) {
            expression = expression.and(Expressions.allOf(relationship.receiverPerson.lastName.containsIgnoreCase(criteria.getLastName())
                                                                                              .and(relationship.receiverPersonId.ne(criteria.getPersonId())),
                    relationship.requesterPerson.lastName.containsIgnoreCase(criteria.getLastName())
                                                         .and(relationship.requesterPersonId.ne(criteria.getPersonId()))));
        }

        if (StringUtils.isNotBlank(criteria.getLastName())) {
            expression = expression.and(Expressions.allOf(relationship.receiverPerson.firstName.containsIgnoreCase(criteria.getFirstName())
                                                                                               .and(relationship.receiverPersonId.ne(criteria.getPersonId())),
                    relationship.requesterPerson.firstName.containsIgnoreCase(criteria.getFirstName())
                                                          .and(relationship.requesterPersonId.ne(criteria.getPersonId()))));
        }

        if (!criteria.getStatuses().isEmpty()) {
            expression = expression.and(relationship.status.in(criteria.getStatuses()));
        }

        if (!criteria.getRelationTypes().isEmpty()) {
            expression = expression.and(relationship.relationType.in(criteria.getRelationTypes()));
        }

        if (!criteria.getRelationVisibilities().isEmpty()) {
            expression = expression.and(Expressions.anyOf(relationship.receiverRelationVisibility.in(criteria.getRelationVisibilities())
                                                                                                 .and(relationship.receiverPersonId.eq(criteria.getPersonId())),
                    relationship.requesterRelationVisibility.in(criteria.getRelationVisibilities())
                                                            .and(relationship.requesterPersonId.eq(criteria.getPersonId()))));
        }

        return expression;
    }

    private BooleanExpression mandatoryRelationshipPredicate(QRelationshipJpaEntity relationship, UUID authenticatedPersonId, UUID personId) {
        return Expressions.anyOf(relationship.receiverPersonId.eq(authenticatedPersonId).and(relationship.requesterPersonId.eq(personId)),
                relationship.requesterPersonId.eq(authenticatedPersonId).and(relationship.receiverPersonId.eq(personId))
        );
    }

    private BooleanExpression visibleRelationshipPredicate(QRelationshipJpaEntity relationship, UUID personId, RelationType targetRelationType) {
        return relationship.status.eq(RelationshipStatus.ACTIVE)
                                  .and(Expressions.anyOf(relationship.receiverPersonId.eq(personId)
                                                                                      .and(relationship.receiverRelationVisibility
                                                                                              .in(relationTypeLevelsResolver(targetRelationType))),
                                          relationship.requesterPersonId.eq(personId)
                                                                        .and(relationship.requesterRelationVisibility
                                                                                .in(relationTypeLevelsResolver(targetRelationType))
                                                                        )));
    }

    private List<RelationType> relationTypeLevelsResolver(RelationType relationType) {
        return Arrays.stream(RelationType.values())
                     .filter(it -> relationType.getLevel() >= it.getLevel() && it != RelationType.NONE)
                     .toList();
    }

}
