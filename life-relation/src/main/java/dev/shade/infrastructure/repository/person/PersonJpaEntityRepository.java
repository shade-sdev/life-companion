package dev.shade.infrastructure.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonJpaEntityRepository extends JpaRepository<PersonJpaEntity, UUID> {

    Optional<PersonJpaEntity> findPersonJpaEntityByUserId(UUID userId);

    @Query("SELECT p.id FROM PersonJpaEntity p" +
            " WHERE p.userId = :userId")
    Optional<UUID> findPersonIdByUserId(UUID userId);
}
