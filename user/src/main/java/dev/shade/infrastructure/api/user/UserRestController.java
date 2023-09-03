package dev.shade.infrastructure.api.user;

import dev.shade.service.user.UserService;
import dev.shade.service.user.model.UserApiBean;
import dev.shade.service.user.model.UserUpdate;
import dev.shade.service.user.model.UserUpdateRequestApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserApiBean> getUserById(UUID userId) {
        return ResponseEntity.of(userService.findById(userId).map(mapper::mapToUserApiBean));
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID userId, UserUpdateRequestApiBean userUpdateRequestApiBean) {
        userService.updateUser(userId, UserUpdate.builder()
                                                 .firstName(userUpdateRequestApiBean.getFirstName())
                                                 .lastName(userUpdateRequestApiBean.getLastName())
                                                 .email(userUpdateRequestApiBean.getEmail())
                                                 .build());
        return ResponseEntity.ok().build();
    }
}
