package dev.shade.shared.domain;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class Auditable implements Serializable {

    LocalDateTime createdDate;

    String createdBy;

    LocalDateTime lastModifiedDate;

    String lastModifiedBy;
}
