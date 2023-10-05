package dev.shade.infrastructure.repository.person;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJpaEntityRepository extends JpaRepository<PersonJpaEntity, UUID>, QuerydslPredicateExecutor<PersonJpaEntity> {

    Optional<PersonJpaEntity> findPersonJpaEntityByUserId(UUID userId);

    @Query("SELECT p.id FROM PersonJpaEntity p" +
            " WHERE p.userId = :userId")
    Optional<UUID> findPersonIdByUserId(UUID userId);
}
