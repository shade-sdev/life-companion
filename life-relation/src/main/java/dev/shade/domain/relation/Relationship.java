package dev.shade.domain.relation;

import dev.shade.shared.domain.Auditable;
import dev.shade.shared.exception.InvalidStateException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
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
    @Valid
    @Default
    RelationshipPerson requesterPerson = RelationshipPerson.builder().build();

    @NotNull
    @Valid
    @Default
    RelationshipPerson receiverPerson = RelationshipPerson.builder().build();

    @Default
    @NotNull
    RelationType relationType = RelationType.STRANGER;

    @Default
    RelationshipStatus status = RelationshipStatus.PENDING;

    @Default
    Auditable auditable = Auditable.builder().build();

    Long version;

    @Value
    @Builder(toBuilder = true)
    public static class RelationshipPerson implements Serializable {

        @NotNull
        UUID id;

        UUID userId;

        String firstName;

        String lastName;
    }

    public Relationship initializeRequest(UUID requesterPersonId) {
        if (RelationshipStatus.PENDING != this.getStatus() ||
                !this.getRequesterPerson().getId().equals(requesterPersonId) ||
                List.of(RelationType.NONE, RelationType.ALL).contains(this.getRelationType())
        ) {
            throw new InvalidStateException(Relationship.class);
        }

        return this.toBuilder()
                   .status(RelationshipStatus.PENDING)
                   .build();
    }

    public Relationship acceptRequest(UUID receiverPersonId) {
        if (RelationshipStatus.PENDING != this.getStatus() ||
                !this.getReceiverPerson().getId().equals(receiverPersonId) ||
                List.of(RelationType.NONE, RelationType.ALL).contains(this.getRelationType())
        ) {
            throw new InvalidStateException(this.getId(), Relationship.class);
        }

        return this.toBuilder()
                   .status(RelationshipStatus.ACTIVE)
                   .build();
    }

}
