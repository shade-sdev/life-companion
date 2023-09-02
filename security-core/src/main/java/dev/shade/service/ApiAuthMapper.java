package dev.shade.service;

import dev.shade.domain.user.User;
import dev.shade.model.UserCreationRequestApiBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiAuthMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "isAccountNonLocked", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    User mapToUser(UserCreationRequestApiBean user);

}
