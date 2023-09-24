package dev.shade.domain.relation;

import dev.shade.shared.domain.Auditable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import java.io.Serializable;
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
    UUID firstPersonId;

    @NotNull
    UUID secondPersonId;

    @Default
    @NotNull
    RelationType relationType = RelationType.STRANGER;

    @Default
    boolean active = false;

    @Default
    Auditable auditable = Auditable.builder().build();

}
