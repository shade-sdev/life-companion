package dev.shade.infrastructure.api.user;

import dev.shade.domain.user.User;
import dev.shade.service.user.model.UserApiBean;
import dev.shade.service.user.model.UserUpdateRequestApiBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiUserMapper {

    @Mapping(target = "security", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    User mapToUser(UserUpdateRequestApiBean user);

    @Mapping(target = "role", source = "role.name")
    UserApiBean mapToUserApiBean(User user);
}
