package dev.shade.infrastructure.api.user;

import dev.shade.application.model.user.UserApiBean;
import dev.shade.application.model.user.UserUpdateRequestApiBean;
import dev.shade.application.service.user.UserService;
import dev.shade.application.model.user.UserUpdate;
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
        return ResponseEntity.ok(mapper.mapToUserApiBean(userService.findById(userId)));
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID userId, UserUpdateRequestApiBean userUpdateRequestApiBean) {
        userService.updateUser(userId, UserUpdate.builder()
                                                 .email(userUpdateRequestApiBean.getEmail())
                                                 .build());
        return ResponseEntity.ok().build();
    }
}
