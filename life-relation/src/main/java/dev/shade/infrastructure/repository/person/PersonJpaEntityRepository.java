package dev.shade.infrastructure.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonJpaEntityRepository extends JpaRepository<PersonJpaEntity, UUID> {
}
