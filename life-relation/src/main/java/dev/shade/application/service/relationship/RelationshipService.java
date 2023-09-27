package dev.shade.application.service.relationship;

import dev.shade.domain.relation.RelationType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface RelationshipService {

    void requestRelationship(@NotNull UUID requesterPersonId, @NotNull UUID receiverPersonId, @NotNull RelationType relationType);

    void acceptRelationshipRequest(@NotNull UUID relationshipId);
}
