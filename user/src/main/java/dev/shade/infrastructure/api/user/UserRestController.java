package dev.shade.infrastructure.api.user;

import dev.shade.domain.user.User;
import dev.shade.service.user.UserService;
import dev.shade.service.user.model.UserApiBean;
import dev.shade.service.user.model.UserCreationRequestApiBean;
import dev.shade.service.user.model.UserUpdateRequestApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserRestController implements UsersApi {

    private final UserService userService;
    private final ApiUserMapper mapper;

    @Autowired
    public UserRestController(UserService userService, ApiUserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> createUser(UserCreationRequestApiBean userCreationRequestApiBean) {
        User user = userService.createUser(mapper.mapToUser(userCreationRequestApiBean));
        return ResponseEntity.created(UriComponentsBuilder
                        .fromUriString("/api/v1/users/" + user.getId())
                        .buildAndExpand(user.getId())
                        .toUri())
                .build();
    }

    @Override
    public ResponseEntity<UserApiBean> getUserById(UUID userId) {
        return ResponseEntity.of(userService.findById(userId).map(mapper::mapToUserApiBean));
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID userId, UserUpdateRequestApiBean userUpdateRequestApiBean) {
        userService.updateUser(userId, mapper.mapToUser(userUpdateRequestApiBean));
        return ResponseEntity.ok().build();
    }
}
