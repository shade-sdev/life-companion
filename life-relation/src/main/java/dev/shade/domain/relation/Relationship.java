package dev.shade.domain.relation;

import dev.shade.shared.domain.Auditable;
import dev.shade.shared.exception.InvalidStateException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

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

    public Relationship initializeRequest() {
        if (Objects.nonNull(this.getStatus())) {
            throw new InvalidStateException(Relationship.class);
        }

        return this.toBuilder()
                   .status(RelationshipStatus.PENDING)
                   .build();
    }

    public Relationship acceptRequest() {
        if (RelationshipStatus.PENDING != this.getStatus()) {
            throw new InvalidStateException(this.getId(), Relationship.class);
        }

        return this.toBuilder()
                   .status(RelationshipStatus.ACTIVE)
                   .build();
    }

}
