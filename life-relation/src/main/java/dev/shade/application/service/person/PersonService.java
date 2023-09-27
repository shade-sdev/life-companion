package dev.shade.application.service.person;

import dev.shade.application.model.person.PersonRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface PersonService {

    void updatePerson(@NotNull UUID personId, @NotNull @Valid PersonRequest personRequest);
}
