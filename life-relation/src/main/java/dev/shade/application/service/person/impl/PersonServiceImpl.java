package dev.shade.application.service.person.impl;

import dev.shade.application.service.person.PersonService;
import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person save(Person person) {
        return repository.save(person);
    }
}
