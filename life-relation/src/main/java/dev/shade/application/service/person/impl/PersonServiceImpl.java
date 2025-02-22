package dev.shade.application.service.person.impl;

import dev.shade.application.model.person.PersonRequest;
import dev.shade.application.model.person.PersonSearchCriteria;
import dev.shade.application.service.person.PersonService;
import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.infrastructure.configuration.security.PersonSecurityHelper;
import dev.shade.shared.exception.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    private final PersonSecurityHelper personSecurityHelper;
    private final Validator validator;

    @Autowired
    public PersonServiceImpl(PersonRepository repository,
                             PersonMapper mapper,
                             PersonSecurityHelper personSecurityHelper,
                             Validator validator
                            ) {
        this.repository = repository;
        this.mapper = mapper;
        this.personSecurityHelper = personSecurityHelper;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Person> search(PersonSearchCriteria criteria, Integer pageNumber, Integer pageSize) {
        return repository.search(personSecurityHelper.securityFiler(criteria), pageNumber, pageSize);
    }

    @Override
    public Optional<Person> getPersonByUserId(UUID userId) {
        return repository.findByUserId(userId)
                         .map(personSecurityHelper::securityFilter);
    }

    @Override
    @Transactional
    public void updatePerson(@NotNull UUID personId, @NotNull @Valid PersonRequest personRequest) {
        Person currentPerson = repository.findById(personId)
                                         .orElseThrow(() -> new NotFoundException(personId, Person.class));
        Person updatedData = mapper.mapToPerson(personRequest);
        repository.save(currentPerson.update(updatedData, personSecurityHelper.username()).validate(validator));
    }
}
