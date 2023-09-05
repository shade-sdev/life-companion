package dev.shade.service.impl;

import dev.shade.domain.user.Permission;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.User;
import dev.shade.shared.security.model.RoleCode;
import dev.shade.shared.security.model.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SecurityMapper {

    @Mapping(target = "password", source = "security.password")
    @Mapping(target = "isAccountNonLocked", source = "security.accountNonLocked")
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
