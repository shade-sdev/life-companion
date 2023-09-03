package dev.shade.service.impl;

import dev.shade.domain.user.Permission;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.User;
import dev.shade.shared.security.permissionevalutor.model.RoleCode;
import dev.shade.shared.security.permissionevalutor.model.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SecurityMapper {

    @Mapping(target = "isAccountNonLocked", source = "accountNonLocked")
    UserPrincipal mapToUserPrincipal(User user);

    default UserPrincipal.Role mapToUserPrincipalRole(Role role) {
        return UserPrincipal.Role.builder()
                                 .roleCode(RoleCode.valueOf(role.getName().name()))
                                 .permissions(role.getPermissions()
                                                  .stream()
                                                  .map(Permission::getName)
                                                  .collect(Collectors.toSet()))
                                 .build();
    }

}
