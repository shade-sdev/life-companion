package dev.shade.domain.repository;

import dev.shade.domain.person.Person;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> findById(UUID personId);

    Optional<UUID> findPersonIdByUserId(UUID userId);

    Optional<Person> findByUserId(UUID userId);

    Person save(Person person);
}
