package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.person.Person;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.shared.exception.NotFoundException;
import dev.shade.shared.security.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PersonSecurityHelper extends SecurityContextHelper {

    private final PersonRepository personRepository;
    private final RelationshipRepository relationshipRepository;

    @Autowired
    public PersonSecurityHelper(PersonRepository personRepository, RelationshipRepository relationshipRepository) {
        this.personRepository = personRepository;
        this.relationshipRepository = relationshipRepository;
    }

    public Person securityFilter(Person person) {
        return switch (this.roleCode()) {
            case ROLE_ADMIN -> person;
            case ROLE_USER -> {
                UUID requesterPersonId = personRepository.findPersonIdByUserId(userId())
                                                         .orElseThrow(() -> new NotFoundException(userId(), "userId", Person.class));

                RelationType targetRelationType = relationshipRepository.getRelationType(requesterPersonId, person.getId())
                                                                        .orElse(RelationType.ALL);

                Person.PersonBuilder builder = person.toBuilder();

                Optional.of(person)
                        .map(Person::getIdentity)
                        .map(Person.Identity::getIdentityVisibility)
                        .ifPresent(it -> {
                            if (resolveVisibility(targetRelationType, it, requesterPersonId, person.getId())) {
                                builder.identity(null);
                            }
                        });

                Optional.of(person)
                        .map(Person::getContact)
                        .map(Person.Contact::getContactVisibility)
                        .ifPresent(it -> {
                            if (resolveVisibility(targetRelationType, it, requesterPersonId, person.getId())) {
                                builder.contact(null);
                            }
                        });

                Optional.of(person)
                        .map(Person::getAddress)
                        .map(Person.Address::getAddressVisibility)
                        .ifPresent(it -> {
                            if (resolveVisibility(targetRelationType, it, requesterPersonId, person.getId())) {
                                builder.address(null);
                            }
                        });

                yield builder.build();
            }
        };
    }

    private boolean resolveVisibility(RelationType targetRelationType,
                                      RelationType actualRelationType,
                                      UUID requesterPersonId,
                                      UUID acutalPersonId
    ) {
        if (actualRelationType == RelationType.NONE && !requesterPersonId.equals(acutalPersonId)) {
            return true;
        }
        return targetRelationType.getLevel() < actualRelationType.getLevel() && !requesterPersonId.equals(acutalPersonId);
    }

}
