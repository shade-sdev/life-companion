package dev.shade.infrastructure.repository.person.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;

import dev.shade.application.model.person.PersonSearchCriteria;
import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.infrastructure.repository.person.JpaPersonMapper;
import dev.shade.infrastructure.repository.person.PersonJpaEntity;
import dev.shade.infrastructure.repository.person.PersonJpaEntityRepository;
import dev.shade.infrastructure.repository.person.QPersonJpaEntity;

@Repository
public class JpaPersonRepositoryImpl implements PersonRepository {

    private final PersonJpaEntityRepository repository;
    private final JpaPersonMapper mapper;

    @Autowired
    public JpaPersonRepositoryImpl(PersonJpaEntityRepository repository, JpaPersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<Person> search(PersonSearchCriteria criteria, Integer pageNumber, Integer pageSize) {
        return mapper.mapToPersonPage(repository.findAll(toPredicate(criteria), PageRequest.of(pageNumber, pageSize)));
    }

    @Override
    public Optional<Person> findById(UUID personId) {
        return repository.findById(personId).map(mapper::mapToPerson);
    }

    @Override
    public Optional<UUID> findPersonIdByUserId(UUID userId) {
        return repository.findPersonIdByUserId(userId);
    }

    @Override
    public Optional<Person> findByUserId(UUID userId) {
        return repository.findPersonJpaEntityByUserId(userId).map(mapper::mapToPerson);
    }

    @Override
    public Person save(Person person) {
        PersonJpaEntity personJpaEntity = repository.save(mapper.mapToEntity(person));
        return mapper.mapToPerson(personJpaEntity);
    }

    private Predicate toPredicate(PersonSearchCriteria criteria) {
        var person = QPersonJpaEntity.personJpaEntity;
        BooleanExpression expression = Expressions.TRUE.isTrue();

        if (StringUtils.isNotBlank(criteria.getFirstName())) {
            expression = expression.and(Expressions.anyOf(
                    relatedPredicate(person, criteria.getAuthenticatedPersonId(), person.identity.firstName, criteria.getFirstName()),
                    person.identity.firstName.eq(criteria.getFirstName())
            ));
        }

        if (StringUtils.isNotBlank(criteria.getLastName())) {
            expression = expression.and(Expressions.anyOf(
                    relatedPredicate(person, criteria.getAuthenticatedPersonId(), person.identity.lastName, criteria.getLastName()),
                    person.identity.lastName.eq(criteria.getLastName())
            ));
        }

        if (StringUtils.isBlank(criteria.getFirstName()) && StringUtils.isBlank(criteria.getLastName())) {
            return Expressions.TRUE.isFalse();
        }

        return expression;
    }

    private BooleanExpression relatedPredicate(QPersonJpaEntity person, UUID authenticatedPersonId, StringPath path, String search) {
        return person.receiverRelations.any().receiverPersonId.eq(authenticatedPersonId)
                                                              .or(person.requesterRelations.any().requesterPersonId.eq(authenticatedPersonId))
                                                              .and(path.containsIgnoreCase(search));
    }

}
