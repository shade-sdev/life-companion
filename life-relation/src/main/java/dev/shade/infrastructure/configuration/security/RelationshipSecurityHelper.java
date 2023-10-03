package dev.shade.infrastructure.configuration.security;

import dev.shade.application.model.relationship.RelationshipSearchCriteria;
import dev.shade.domain.person.Person;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.shared.exception.NotFoundException;
import dev.shade.shared.security.SecurityContextHelper;
import dev.shade.shared.security.model.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RelationshipSecurityHelper extends SecurityContextHelper {

    private final RelationshipRepository relationshipRepository;
    private final PersonRepository personRepository;

    @Autowired
    public RelationshipSecurityHelper(RelationshipRepository relationshipRepository, PersonRepository personRepository) {
        this.relationshipRepository = relationshipRepository;
        this.personRepository = personRepository;
    }

    public RelationshipSearchCriteria securityFiler(RelationshipSearchCriteria searchCriteria) {
        RelationshipSearchCriteria.RelationshipSearchCriteriaBuilder builder = searchCriteria.toBuilder();

        UUID authenticatedPersonId = personRepository.findPersonIdByUserId(userId())
                                                     .orElseThrow(() -> new NotFoundException(userId(), "userId", Person.class));

        RelationType targetRelationType = relationshipRepository.getRelationType(authenticatedPersonId, searchCriteria.getPersonId())
                                                                .orElse(RelationType.ALL);

        if (roleCode() == RoleCode.ROLE_USER && !searchCriteria.getPersonId().equals(authenticatedPersonId)) {
            builder.authenticatedPersonId(authenticatedPersonId)
                   .targetRelationType(targetRelationType);
        }

        return builder.build();
    }

}
