package dev.shade.application.model.person;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Min(8)
    @Max(8)
    @Pattern(regexp = "^5\\d{7}$", message = "Invalid phone number")
    Integer mobileNumber;

    @Min(7)
    @Max(7)
    Integer homeNumber;

    PersonDataVisibility contactVisibility;
}
