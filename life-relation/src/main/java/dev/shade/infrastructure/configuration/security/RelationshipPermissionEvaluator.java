package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.person.Person;
import dev.shade.domain.relation.Relationship;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.domain.repository.RelationshipRepository;
import dev.shade.shared.exception.NotFoundException;
import dev.shade.shared.security.SecurityContextHelper;
import dev.shade.shared.security.TargetedPermissionEvaluator;
import dev.shade.shared.security.model.PermissionScope;
import dev.shade.shared.security.model.RoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static dev.shade.shared.security.model.PermissionScope.MINE;
import static dev.shade.shared.security.model.PermissionScope.OTHER;

@Component
public class RelationshipPermissionEvaluator implements TargetedPermissionEvaluator {

    private final RelationshipRepository relationshipRepository;
    private final PersonRepository personRepository;

    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public RelationshipPermissionEvaluator(RelationshipRepository relationshipRepository,
                                           PersonRepository personRepository,
                                           SecurityContextHelper securityContextHelper
    ) {
        this.relationshipRepository = relationshipRepository;
        this.personRepository = personRepository;
        this.securityContextHelper = securityContextHelper;
    }

    @Override
    public String getTargetType() {
        return Relationship.class.getName();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Optional<UUID> relationshipId = Optional.ofNullable(targetId)
                                                .map(Object::toString)
                                                .map(UUID::fromString);

        if (relationshipId.isEmpty()) {
            return securityContextHelper.resolvePermission(String.valueOf(permission));
        }

        UUID currentPersonId = personRepository.findPersonIdByUserId(securityContextHelper.userId())
                                               .orElseThrow(() -> new NotFoundException(securityContextHelper.userId(), "userId", Person.class));

        PermissionScope scope = relationshipId.flatMap(relationshipRepository::findById)
                                              .map(it -> hasAccess(it, currentPersonId, securityContextHelper.roleCode()))
                                              .map(it -> Boolean.TRUE.equals(it) ? MINE : OTHER)
                                              .orElse(OTHER);

        return securityContextHelper.resolvePermission(scope, String.valueOf(permission));
    }

    private boolean hasAccess(Relationship relationship, UUID currentPersonId, RoleCode code) {
        return switch (code) {
            case ROLE_USER -> Optional.of(relationship)
                                      .map(it -> Stream.of(it.getRequesterPerson().getId(), it.getReceiverPerson().getId()))
                                      .stream()
                                      .flatMap(Function.identity())
                                      .anyMatch(currentPersonId::equals);
            case ROLE_ADMIN -> false;
        };
    }
}
