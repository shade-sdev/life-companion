package dev.shade.infrastructure.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaEntityRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByUserName(String username);
}
