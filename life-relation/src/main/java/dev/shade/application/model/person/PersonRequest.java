package dev.shade.application.model.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
@Builder
public class PersonRequest {

    @NotBlank
    @Length(min = 1, max = 255)
    String firstName;

    @NotBlank
    @Length(min = 1, max = 255)
    String lastName;

    PersonDataVisibility identityVisibility;

    Long houseNumber;

    @Length(min = 1, max = 255)
    String streetName;

    LocalityEnum locality;

    PersonDataVisibility addressVisibility;

    @Size(min = 8)
    @Size(max = 8)
    @Pattern(regexp = "^5\\d{7}$", message = "Invalid phone number")
    String mobileNumber;

    @Size(min = 7)
    @Size(max = 7)
    String homeNumber;

    PersonDataVisibility contactVisibility;
}
