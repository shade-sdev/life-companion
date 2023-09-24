package dev.shade.domain.repository;

import dev.shade.domain.person.Person;

public interface PersonRepository {
    Person save(Person person);
}
