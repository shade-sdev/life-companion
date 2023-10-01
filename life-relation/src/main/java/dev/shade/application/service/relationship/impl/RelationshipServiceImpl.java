package dev.shade.application.service.relationship.impl;

import dev.shade.application.model.relationship.RelationshipSearchCriteria;
import dev.shade.application.service.relationship.RelationshipService;
import dev.shade.domain.person.Person;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.infrastructure.configuration.security.RelationshipSecurityHelper;
import dev.shade.shared.exception.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.UUID;

@Service
@Validated
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository repository;
    private final PersonRepository personRepository;

    private final RelationshipSecurityHelper relationshipSecurityHelper;

    @Autowired
    public RelationshipServiceImpl(RelationshipRepository repository,
                                   PersonRepository personRepository,
                                   RelationshipSecurityHelper relationshipSecurityHelper
    ) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.relationshipSecurityHelper = relationshipSecurityHelper;
    }

    @Override
    public Page<Relationship> search(@Valid @NotNull RelationshipSearchCriteria searchCriteria,
                                     Integer pageNumber,
                                     Integer pageSize
    ) {
        Page<Relationship> search = repository.search(searchCriteria, pageNumber, pageSize);
        return new PageImpl<>(relationshipSecurityHelper.securityFilter(
                new HashSet<>(search.getContent()),
                searchCriteria.getPersonId()).stream().toList(),
                search.getPageable(),
                search.getTotalElements());
    }

    @Override
    @Transactional
    public Relationship requestRelationship(@NotNull UUID requesterPersonId,
                                            @NotNull UUID receiverPersonId,
                                            @NotNull RelationType relationType
    ) {
        Relationship newRelationship = Relationship.builder()
                                                   .requesterPerson(Relationship.RelationshipPerson
                                                           .builder()
                                                           .id(requesterPersonId)
                                                           .build())
                                                   .receiverPerson(Relationship.RelationshipPerson
                                                           .builder()
                                                           .id(receiverPersonId)
                                                           .build())
                                                   .relationType(relationType)
                                                   .build();

        UUID authenticatedPersonId = personRepository.findPersonIdByUserId(relationshipSecurityHelper.userId())
                                                     .orElseThrow(() -> new NotFoundException(relationshipSecurityHelper.userId(), "userId", Person.class));

        return repository.save(newRelationship.initializeRequest(authenticatedPersonId));
    }

    @Override
    @Transactional
    public void acceptRelationshipRequest(@NotNull UUID relationshipId) {
        Relationship pendingRelationship = repository.findById(relationshipId)
                                                     .orElseThrow(() -> new NotFoundException(relationshipId, Relationship.class));

        UUID authenticatedPersonId = personRepository.findPersonIdByUserId(relationshipSecurityHelper.userId())
                                                     .orElseThrow(() -> new NotFoundException(relationshipSecurityHelper.userId(), "userId", Person.class));

        repository.save(pendingRelationship.acceptRequest(authenticatedPersonId, relationshipSecurityHelper.username()));
    }
}
