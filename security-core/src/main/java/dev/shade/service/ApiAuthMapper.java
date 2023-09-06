package dev.shade.service;

import dev.shade.domain.user.User;
import dev.shade.model.UserAuthRequestApiBean;
import dev.shade.model.UserCreationRequestApiBean;
import dev.shade.service.model.UserAuthenticationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiAuthMapper {

    @Mapping(target = "security.isTwoFactorEnabled", source = "isTwoFactorEnabled")
    @Mapping(target = "security.password", source = "password")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    User mapToUser(UserCreationRequestApiBean user);

    @Mapping(target = "username", source = "userName")
    UserAuthenticationRequest mapToUserAuthenticationRequest(UserAuthRequestApiBean userAuthRequestApiBean);

}
