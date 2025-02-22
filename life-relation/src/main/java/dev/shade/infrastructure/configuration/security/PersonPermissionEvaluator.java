package dev.shade.infrastructure.configuration.security;

import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
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

import static dev.shade.shared.security.model.PermissionScope.MINE;
import static dev.shade.shared.security.model.PermissionScope.OTHER;

@Component
public class PersonPermissionEvaluator implements TargetedPermissionEvaluator {

    private final PersonRepository personRepository;

    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public PersonPermissionEvaluator(PersonRepository personRepository, SecurityContextHelper securityContextHelper) {
        this.personRepository = personRepository;
        this.securityContextHelper = securityContextHelper;
    }

    @Override
    public String getTargetType() {
        return Person.class.getName();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Optional<UUID> id = Optional.ofNullable(targetId)
                                    .map(Object::toString)
                                    .map(UUID::fromString);

        if (id.isEmpty()) {
            return securityContextHelper.resolvePermission(String.valueOf(permission));
        }

        PermissionScope scope = id.map(it -> hasAccess(it, securityContextHelper.roleCode()))
                                  .map(it -> Boolean.TRUE.equals(it) ? MINE : OTHER)
                                  .orElse(OTHER);

        return securityContextHelper.resolvePermission(scope, String.valueOf(permission));
    }

    private boolean hasAccess(UUID personIdOrUserId, RoleCode code) {
        return switch (code) {
            case ROLE_USER -> personRepository.findPersonIdByUserId(securityContextHelper.userId())
                                              .map(personIdOrUserId::equals)
                                              .orElse(personIdOrUserId.equals(securityContextHelper.userId()));
            case ROLE_ADMIN -> false;
        };
    }
}
