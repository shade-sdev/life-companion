package dev.shade.infrastructure.repository.person.impl;

import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.infrastructure.repository.person.JpaPersonMapper;
import dev.shade.infrastructure.repository.person.PersonJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

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
        return mapper.mapToPerson(repository.save(mapper.mapToEntity(person)));
    }
}
