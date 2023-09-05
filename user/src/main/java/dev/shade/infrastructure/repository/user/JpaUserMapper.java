package dev.shade.infrastructure.repository.user;

import dev.shade.domain.Auditable;
import dev.shade.domain.user.Role;
import dev.shade.domain.user.User;
import dev.shade.infrastructure.repository.authority.RoleJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JpaUserMapper {

    @Mapping(target = "createdBy", source = "audit.createdBy")
    @Mapping(target = "lastModifiedBy", source = "audit.lastModifiedBy")
    @Mapping(target = "createdDate", source = "audit.createdDate")
    @Mapping(target = "lastModifiedDate", source = "audit.lastModifiedDate")
    @Mapping(target = "security.isAccountNonLocked", source = "security.accountNonLocked")
    @Mapping(target = "security.isTwoFactorEnabled", source = "security.twoFactorEnabled")
    UserJpaEntity mapToEntity(User user);

    @Mapping(target = "audit", source = ".")
    @Mapping(target = "security.isAccountNonLocked", source = "security.accountNonLocked")
    @Mapping(target = "security.isTwoFactorEnabled", source = "security.twoFactorEnabled")
    User mapToUser(UserJpaEntity userJpaEntity);

    default Auditable mapToAuditable(UserJpaEntity userJpaEntity) {
        return Auditable.builder()
                .lastModifiedBy(userJpaEntity.getLastModifiedBy())
                .createdBy(userJpaEntity.getCreatedBy())
                .createdDate(userJpaEntity.getCreatedDate())
                .lastModifiedDate(userJpaEntity.getLastModifiedDate())
                .build();
    }

    @Mapping(target = "permissions", ignore = true)
    RoleJpaEntity mapToRoleJpaEntity(Role role);
}
