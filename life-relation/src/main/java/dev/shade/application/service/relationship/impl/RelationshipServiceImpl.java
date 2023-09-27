package dev.shade.application.service.relationship.impl;

import dev.shade.application.service.relationship.RelationshipService;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.shared.exception.NotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class RelationshipServiceImpl implements RelationshipService {

    private final RelationshipRepository repository;

    @Autowired
    public RelationshipServiceImpl(RelationshipRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void requestRelationship(@NotNull UUID requesterPersonId,
                                    @NotNull UUID receiverPersonId,
                                    @NotNull RelationType relationType
    ) {
        Relationship newRelationship = Relationship.builder()
                                                   .requesterPersonId(requesterPersonId)
                                                   .receiverPersonId(receiverPersonId)
                                                   .relationType(relationType)
                                                   .build();

        repository.save(newRelationship.initializeRequest());
    }

    @Override
    @Transactional
    public void acceptRelationshipRequest(@NotNull UUID relationshipId) {
        Relationship pendingRelationship = repository.findById(relationshipId)
                                                     .orElseThrow(() -> new NotFoundException(relationshipId, Relationship.class));

        repository.save(pendingRelationship.acceptRequest());
    }
}
