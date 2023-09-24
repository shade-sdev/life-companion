package dev.shade.domain.person;

import dev.shade.shared.domain.Auditable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Relationship implements Serializable {

    @NotNull
    UUID personId;

    @Default
    @NotNull
    RelationType relationType = RelationType.STRANGER;

    @Default
    boolean active = false;

    @Default
    Auditable auditable = Auditable.builder().build();
}
