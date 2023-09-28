package dev.shade.infrastructure.api.user;

import dev.shade.application.model.user.UserApiBean;
import dev.shade.application.model.user.UserUpdateRequestApiBean;
import dev.shade.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiUserMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "security", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    User mapToUser(UserUpdateRequestApiBean user);

    @Mapping(target = "role", source = "role.name")
    UserApiBean mapToUserApiBean(User user);
}
