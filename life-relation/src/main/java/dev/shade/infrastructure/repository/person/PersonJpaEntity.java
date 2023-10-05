package dev.shade.infrastructure.repository.person;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import dev.shade.domain.person.Locality;
import dev.shade.domain.relation.RelationType;
import dev.shade.infrastructure.repository.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_person_id", referencedColumnName = "id", insertable = false, updatable = false)
    List<PersonRelationshipJpaEntity> requesterRelations;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_person_id", referencedColumnName = "id", insertable = false, updatable = false)
    List<PersonRelationshipJpaEntity> receiverRelations;

    @Column(name = "initialized")
    private boolean initialized;

    @Version
    @Column(name = "version")
    private Long version;

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
