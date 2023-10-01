package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.person.Person;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.domain.relation.RelationshipStatus;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.shared.exception.NotFoundException;
import dev.shade.shared.security.SecurityContextHelper;
import dev.shade.shared.security.model.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RelationshipSecurityHelper extends SecurityContextHelper {

    private final RelationshipRepository relationshipRepository;
    private final PersonRepository personRepository;

    @Autowired
    public RelationshipSecurityHelper(RelationshipRepository relationshipRepository, PersonRepository personRepository) {
        this.relationshipRepository = relationshipRepository;
        this.personRepository = personRepository;
    }

    public Set<Relationship> securityFilter(Set<Relationship> relationships, UUID personId) {
        UUID authenticatedPersonId = personRepository.findPersonIdByUserId(userId())
                                                     .orElseThrow(() -> new NotFoundException(userId(), "userId", Person.class));

        if (RoleCode.ROLE_ADMIN == roleCode()) {
            return relationships;
        } else if (authenticatedPersonId.equals(personId)) {
            return relationships.stream()
                                .map(it -> additionalFiltering(it, personId))
                                .collect(Collectors.toSet());
        }

        Stream<Relationship> mandatoryRelationships = relationships.stream()
                                                                   .filter(mandatoryRelationshipPredicate(authenticatedPersonId));

        RelationType targetRelationType = relationshipRepository.getRelationType(authenticatedPersonId, personId)
                                                                .orElse(RelationType.ALL);

        Stream<Relationship> visibleRelationships = relationships.stream()
                                                                 .filter(visibleRelationshipPredicate(personId, targetRelationType));

        return Stream.concat(mandatoryRelationships, visibleRelationships)
                     .map(it -> additionalFiltering(it, personId))
                     .collect(Collectors.toSet());
    }

    private Relationship additionalFiltering(Relationship relationship, UUID targetPersonId) {
        Relationship.RelationshipPerson requesterPerson = relationship.getRequesterPerson();
        RelationType requesterRelationshipVisibility = relationship.getRequesterRelationVisibility();
        Relationship.RelationshipPerson receiverPerson = relationship.getReceiverPerson();
        RelationType receiverRelationshipVisibility = relationship.getReceiverRelationVisibility();

        Relationship.RelationshipPerson.RelationshipPersonBuilder visiblePersonBuilder = Relationship.RelationshipPerson.builder();
        RelationType visibleRelationVisibility;
        if (!targetPersonId.equals(requesterPerson.getId())) {
            visiblePersonBuilder.id(receiverPerson.getId())
                                .firstName(receiverPerson.getFirstName())
                                .lastName(receiverPerson.getLastName());
            visibleRelationVisibility = receiverRelationshipVisibility;

        } else {
            visiblePersonBuilder.id(requesterPerson.getId())
                                .firstName(requesterPerson.getFirstName())
                                .lastName(requesterPerson.getLastName());
            visibleRelationVisibility = requesterRelationshipVisibility;
        }

        return relationship.toBuilder()
                           .requesterPerson(visiblePersonBuilder.build())
                           .requesterRelationVisibility(visibleRelationVisibility)
                           .receiverPerson(null)
                           .receiverRelationVisibility(null)
                           .build();
    }

    private Predicate<Relationship> mandatoryRelationshipPredicate(UUID authenticatedPersonId) {
        return relationship -> authenticatedPersonId.equals(relationship.getRequesterPerson().getId())
                || authenticatedPersonId.equals(relationship.getReceiverPerson().getId());
    }

    private Predicate<Relationship> visibleRelationshipPredicate(UUID personId, RelationType targetRelationType) {
        return relationship -> RelationshipStatus.ACTIVE == relationship.getStatus() &&
                personId.equals(relationship.getReceiverPerson().getId())
                ? targetRelationType.getLevel() >= relationship.getReceiverRelationVisibility().getLevel()
                : targetRelationType.getLevel() >= relationship.getRequesterRelationVisibility().getLevel();
    }
}
