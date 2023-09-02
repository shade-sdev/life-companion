package dev.shade.api;

import dev.shade.domain.user.User;
import dev.shade.model.UserAuthRequestApiBean;
import dev.shade.model.UserAuthenticatedResponseApiBean;
import dev.shade.model.UserCreationRequestApiBean;
import dev.shade.service.ApiAuthMapper;
import dev.shade.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class AuthRestController implements AuthApi {

    private final AuthenticationService authenticationService;
    private final ApiAuthMapper mapper;

    @Autowired
    public AuthRestController(AuthenticationService authenticationService, ApiAuthMapper mapper) {
        this.authenticationService = authenticationService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> createUser(UserCreationRequestApiBean userCreationRequestApiBean) {
        User user = authenticationService.createUser(mapper.mapToUser(userCreationRequestApiBean));
        return ResponseEntity.created(UriComponentsBuilder
                                     .fromUriString("/api/v1/users/" + user.getId())
                                     .buildAndExpand(user.getId())
                                     .toUri())
                             .build();
    }

    @Override
    public ResponseEntity<UserAuthenticatedResponseApiBean> authenticateUser(@RequestBody UserAuthRequestApiBean user) {
        return ResponseEntity.ok(authenticationService.authenticateUser(user.getUserName(), user.getPassword()));
    }

}
