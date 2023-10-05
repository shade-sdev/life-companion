package dev.shade.infrastructure.repository.person;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "relationship", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class PersonRelationshipJpaEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "requester_person_id")
    private UUID requesterPersonId;

    @Column(name = "receiver_person_id")
    private UUID receiverPersonId;

}
