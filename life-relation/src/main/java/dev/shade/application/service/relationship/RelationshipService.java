package dev.shade.application.service.relationship;

import dev.shade.domain.relation.RelationType;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

public interface RelationshipService {

    @PreAuthorize("hasPermission(null, 'dev.shade.domain.relation.Relationship', 'Relationship#C')")
    void requestRelationship(@NotNull UUID requesterPersonId, @NotNull UUID receiverPersonId, @NotNull RelationType relationType);

    @PreAuthorize("hasPermission(#relationshipId, 'dev.shade.domain.relation.Relationship', 'Relationship#U')")
    void acceptRelationshipRequest(@NotNull UUID relationshipId);
}