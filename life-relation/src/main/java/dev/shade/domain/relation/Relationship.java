package dev.shade.domain.relation;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import dev.shade.shared.domain.Auditable;
import jakarta.validation.constraints.NotNull;

/**
 * <p>Represents a relationship between individuals. It includes information about the person's ID,
 * the type of relationship, its activity status, and auditing details.</p>
 *
 * @author Shade
 * @version 1.0
 * @see RelationType
 */

@Value
@Builder(toBuilder = true)
public class Relationship implements Serializable {

    @Default
    UUID id = UUID.randomUUID();

    @NotNull
    UUID requesterPersonId;

    @NotNull
    UUID receiverPersonId;

    @Default
    @NotNull
    RelationType relationType = RelationType.STRANGER;

    @Default
    RelationshipStatus status = RelationshipStatus.PENDING;

    @Default
    Auditable auditable = Auditable.builder().build();

}
