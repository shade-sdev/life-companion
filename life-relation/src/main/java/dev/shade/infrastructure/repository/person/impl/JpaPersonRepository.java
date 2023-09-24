package dev.shade.infrastructure.repository.person.impl;

import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.infrastructure.repository.person.JpaPersonMapper;
import dev.shade.infrastructure.repository.person.PersonJpaEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPersonRepository implements PersonRepository {

    private final PersonJpaEntityRepository repository;
    private final JpaPersonMapper mapper;

    @Autowired
    public JpaPersonRepository(PersonJpaEntityRepository repository, JpaPersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Person save(Person person) {
        return mapper.mapToPerson(repository.save(mapper.mapToEntity(person)));
    }
}
