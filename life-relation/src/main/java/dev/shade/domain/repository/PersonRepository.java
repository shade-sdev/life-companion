package dev.shade.domain.repository;

import dev.shade.application.model.person.PersonSearchCriteria;
import dev.shade.domain.person.Person;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

public interface PersonRepository {

    Page<Person> search(PersonSearchCriteria criteria, Integer pageNumber, Integer pageSize);

    Optional<Person> findById(UUID personId);

    Optional<UUID> findPersonIdByUserId(UUID userId);

    Optional<Person> findByUserId(UUID userId);

    Person save(Person person);

}
