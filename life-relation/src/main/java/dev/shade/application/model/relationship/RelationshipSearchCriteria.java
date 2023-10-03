package dev.shade.application.model.relationship;

import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.RelationshipStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class RelationshipSearchCriteria {

    String firstName;

    String lastName;

    @Singular("status")
    List<RelationshipStatus> statuses;

    @Singular("relationType")
    List<RelationType> relationTypes;

    @Singular("relationVisibility")
    List<RelationType> relationVisibilities;

    // Security

    @NotNull
    UUID personId;

    UUID authenticatedPersonId;

    RelationType targetRelationType;
}
