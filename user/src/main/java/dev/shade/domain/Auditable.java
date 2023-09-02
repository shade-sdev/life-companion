package dev.shade.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Value
public class Auditable {

    LocalDateTime createdDate;

    String createdBy;

    LocalDateTime lastModifiedDate;

    String lastModifiedBy;
}
