package dev.shade.infrastructure.repository.person.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;

import dev.shade.application.model.person.PersonSearchCriteria;
import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.infrastructure.repository.person.JpaPersonMapper;
import dev.shade.infrastructure.repository.person.PersonJpaEntity;
import dev.shade.infrastructure.repository.person.PersonJpaEntityRepository;

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
        return null;
    }

}
