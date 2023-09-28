package dev.shade.application.service.person;

import dev.shade.application.model.person.PersonRequest;
import dev.shade.domain.person.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {

    @PreAuthorize("hasPermission(#userId, 'dev.shade.domain.person.Person', 'Person#R')")
    Optional<Person> getPersonByUserId(UUID userId);

    @PreAuthorize("hasPermission(#personId, 'dev.shade.domain.person.Person', 'Person#U')")
    void updatePerson(@NotNull UUID personId, @NotNull @Valid PersonRequest personRequest);
}
