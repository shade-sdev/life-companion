package dev.shade.application.service.relationship;

import dev.shade.application.model.relationship.RelationshipSearchCriteria;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

public interface RelationshipService {

    Page<Relationship> search(@Valid @NotNull RelationshipSearchCriteria searchCriteria, Integer pageNumber, Integer pageSize);

    @PreAuthorize("hasPermission(null, 'dev.shade.domain.relation.Relationship', 'Relationship#C')")
    Relationship requestRelationship(@NotNull UUID requesterPersonId, @NotNull UUID receiverPersonId, @NotNull RelationType relationType);

    @PreAuthorize("hasPermission(#relationshipId, 'dev.shade.domain.relation.Relationship', 'Relationship#U')")
    void acceptRelationshipRequest(@NotNull UUID relationshipId);
}