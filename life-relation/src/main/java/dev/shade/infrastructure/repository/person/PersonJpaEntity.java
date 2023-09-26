package dev.shade.infrastructure.repository.person;

import dev.shade.domain.person.Locality;
import dev.shade.domain.relation.RelationType;
import dev.shade.infrastructure.repository.Auditable;
import dev.shade.infrastructure.repository.relation.RelationshipJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PersonJpaEntity extends Auditable implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Embedded
    private IdentityJpaEntity identity;

    @Embedded
    private AddressJpaEntity address;

    @Embedded
    private ContactJpaEntity contact;

    @ManyToMany
    @JoinTable(
            name = "relationship",
            schema = "public",
            joinColumns = @JoinColumn(name = "requester_person_id", referencedColumnName = "id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "receiver_person_id", referencedColumnName = "id", insertable = false, updatable = false)
    )
    private List<RelationshipJpaEntity> relations;

    @Column(name = "initialized")
    private boolean initialized;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Getter
    public static class IdentityJpaEntity implements Serializable {

        @Column(name = "first_name")
        String firstName;

        @Column(name = "last_name")
        String lastName;

        @Column(name = "identity_visibility")
        @Enumerated(EnumType.STRING)
        RelationType identityVisibility;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Getter
    public static class AddressJpaEntity implements Serializable {

        @Column(name = "house_number")
        Long houseNumber;

        @Column(name = "street_name")
        String streetName;

        @Column(name = "locality")
        @Enumerated(EnumType.STRING)
        Locality locality;

        @Column(name = "address_visibility")
        @Enumerated(EnumType.STRING)
        RelationType addressVisibility = RelationType.NONE;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    @Getter
    public static class ContactJpaEntity implements Serializable {

        @Column(name = "email_address")
        String emailAddress;

        @Column(name = "mobile_number")
        Integer mobileNumber;

        @Column(name = "home_number")
        Integer homeNumber;

        @Column(name = "contact_visibility")
        @Enumerated(EnumType.STRING)
        RelationType contactVisibility = RelationType.NONE;
    }

}
