package dev.shade.shared.security.permissionevalutor;

import dev.shade.shared.security.permissionevalutor.model.AccessCode;
import dev.shade.shared.security.permissionevalutor.model.AccessType;
import dev.shade.shared.security.permissionevalutor.model.PermissionScope;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SecurityPermissionResolver {

    private static final String ACCESS_CONCAT = "%s_%s";

    private static final Map<AccessCode, Function<String, Set<String>>> MINE_PERMISSION = Map.of(

            AccessCode.C, domain -> Stream.of(AccessType.MINE_CREATE, AccessType.MINE_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet()),

            AccessCode.R, domain -> Stream.of(AccessType.MINE_READ, AccessType.MINE_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet()),

            AccessCode.U, domain -> Stream.of(AccessType.MINE_UPDATE, AccessType.MINE_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet()),

            AccessCode.D, domain -> Stream.of(AccessType.MINE_DELETE, AccessType.MINE_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet())
    );

    private static final Map<AccessCode, Function<String, Set<String>>> OTHER_PERMISSION = Map.of(

            AccessCode.C, domain -> Stream.of(AccessType.OTHER_CREATE, AccessType.OTHER_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet()),

            AccessCode.R, domain -> Stream.of(AccessType.OTHER_READ, AccessType.OTHER_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet()),

            AccessCode.U, domain -> Stream.of(AccessType.OTHER_UPDATE, AccessType.OTHER_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet()),

            AccessCode.D, domain -> Stream.of(AccessType.OTHER_DELETE, AccessType.OTHER_MANAGEMENT)
                                          .map(accessType -> String.format(ACCESS_CONCAT, domain, accessType))
                                          .collect(Collectors.toSet())
    );

    public boolean resolvePermission(PermissionScope scope, String permission, Set<String> authorities) {
        Pair<String, AccessCode> pair = getAccessPair(permission);
        Set<String> requiredPermissions = scope == PermissionScope.MINE ? getMinePermissions(pair) : getOtherPermissions(pair);
        return authorities.stream().anyMatch(requiredPermissions::contains);
    }

    private Set<String> getMinePermissions(Pair<String, AccessCode> accessCodePair) {
        return MINE_PERMISSION.get(accessCodePair.getRight()).apply(accessCodePair.getLeft());
    }

    private Set<String> getOtherPermissions(Pair<String, AccessCode> accessCodePair) {
        return OTHER_PERMISSION.get(accessCodePair.getRight()).apply(accessCodePair.getLeft());
    }

    private Pair<String, AccessCode> getAccessPair(String permission) {
        int index = permission.indexOf("#");

        if (index == -1) {
            throw new IllegalArgumentException("Invalid permission format");
        }

        String domainObject = permission.substring(0, index).toUpperCase();

        AccessCode accessCode = Optional.of(permission.substring(index + 1))
                                        .map(AccessCode::valueOf)
                                        .orElseThrow(() -> new IllegalArgumentException("Invalid access code"));

        return Pair.of(domainObject, accessCode);
    }

}
