package dev.shade.application.model.person;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PersonSearchCriteria {

    String firstName;

    String lastName;
}
