package dev.shade.infrastructure.api.user;

import dev.shade.domain.user.User;
import dev.shade.service.user.model.UserApiBean;
import dev.shade.service.user.model.UserCreationRequestApiBean;
import dev.shade.service.user.model.UserUpdateRequestApiBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiUserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isAccountNonLocked", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    User mapToUser(UserCreationRequestApiBean user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isAccountNonLocked", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    User mapToUser(UserUpdateRequestApiBean user);

    @Mapping(target = "role", source = "role.name")
    UserApiBean mapToUserApiBean(User user);
}
