package dev.shade.domain.person;

import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.shared.domain.Auditable;
import dev.shade.shared.domain.DomainValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * <p>This class represents a person in a domain model. It contains information about the person's identity,
 * address, contact details, and relationships. It also includes auditing information and a unique identifier.</p><br>
 *
 * @author Shade
 * @version 1.0
 */
@Value
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class Person extends DomainValidator<Person> implements Serializable {

    @Default
    @NotNull
    UUID id = UUID.randomUUID();

    @NotNull
    UUID userId;

    @Default
    @NotNull
    @Valid
    Identity identity = Identity.builder().build();

    @Default
    @NotNull
    @Valid
    Address address = Address.builder().build();

    @Default
    @NotNull
    @Valid
    Contact contact = Contact.builder().build();

    @Default
    List<Relationship> relations = List.of();

    @Default
    Auditable auditable = Auditable.builder().build();

    @Value
    @Builder(toBuilder = true)
    public static class Identity implements Serializable {

        @NotBlank
        @Length(min = 1, max = 255)
        String firstName;

        @NotBlank
        @Length(min = 1, max = 255)
        String lastName;

        @Default
        RelationType identityVisibility = RelationType.NONE;
    }

    @Value
    @Builder(toBuilder = true)
    public static class Address implements Serializable {

        Long houseNumber;

        @Length(min = 1, max = 255)
        String streetName;

        Locality locality;

        @Default
        RelationType addressVisibility = RelationType.NONE;
    }

    @Value
    @Builder(toBuilder = true)
    public static class Contact implements Serializable {

        @NotBlank
        @Email
        String emailAddress;

        @Min(8)
        @Max(8)
        @Pattern(regexp = "^5\\d{7}$", message = "Invalid phone number")
        Integer mobileNumber;

        @Min(7)
        @Max(7)
        Integer homeNumber;

        @Default
        RelationType contactVisibility = RelationType.NONE;
    }

}
