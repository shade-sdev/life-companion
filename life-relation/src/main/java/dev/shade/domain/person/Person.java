package dev.shade.domain.person;

import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.shared.domain.Auditable;
import dev.shade.shared.domain.DomainValidator;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>This class represents a person in a domain model. It contains information about the person's identity,
 * address, contact details, and relationships. It also includes auditing information and a unique identifier.</p><br>
 *
 * @author Shade
 * @version 1.0
 * @see Relationship
 */
@Value
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
public class Person extends DomainValidator<Person> implements Serializable {

    @NotNull
    @Default
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
    boolean initialized = false;

    @Default
    Auditable auditable = Auditable.builder().build();

    Long version;

    public Person validate(Validator validator) {
        List<Class<?>> groups = new ArrayList<>();
        this.validate(this, validator, groups.toArray(new Class[0]));
        return this;
    }

    public static Person initFromUser(UUID userId, String email) {
        return Person.builder()
                     .userId(userId)
                     .contact(Contact.builder()
                                     .emailAddress(email)
                                     .build())
                     .auditable(Auditable.builder()
                                         .createdBy("system")
                                         .build())
                     .build();
    }

    public Person update(Person updatedData, String updatedBy) {
        return this.toBuilder()
                   .identity(this.getIdentity().toBuilder()
                                 .firstName(updatedData.getIdentity().getFirstName())
                                 .lastName(updatedData.getIdentity().getLastName())
                                 .identityVisibility(updatedData.getIdentity().getIdentityVisibility())
                                 .build())
                   .address(this.getAddress().toBuilder()
                                .locality(updatedData.getAddress().getLocality())
                                .streetName(updatedData.getAddress().getStreetName())
                                .houseNumber(updatedData.getAddress().getHouseNumber())
                                .addressVisibility(updatedData.getAddress().getAddressVisibility())
                                .build())
                   .contact(this.getContact().toBuilder()
                                .homeNumber(updatedData.getContact().getHomeNumber())
                                .mobileNumber(updatedData.getContact().getMobileNumber())
                                .contactVisibility(updatedData.getContact().getContactVisibility())
                                .build())
                   .initialized(true)
                   .auditable(this.getAuditable().toBuilder()
                                  .lastModifiedBy(updatedBy)
                                  .build())
                   .build();
    }

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

        @Size(min = 8)
        @Size(max = 8)
        @Pattern(regexp = "^5\\d{7}$", message = "Invalid phone number")
        String mobileNumber;

        @Size(min = 7)
        @Size(max = 7)
        String homeNumber;

        @Default
        RelationType contactVisibility = RelationType.NONE;
    }

}
