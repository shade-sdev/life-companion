package dev.shade.infrastructure.api;

import dev.shade.application.model.auth.TwoFactorResponseApiBean;
import dev.shade.application.model.auth.UserAuthRequestApiBean;
import dev.shade.application.model.auth.UserAuthenticatedResponseApiBean;
import dev.shade.application.model.auth.UserCreationRequestApiBean;
import dev.shade.domain.user.User;
import dev.shade.application.service.ApiAuthMapper;
import dev.shade.application.service.AuthenticationService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TwoFactorResponseApiBean> createUser(UserCreationRequestApiBean userCreationRequestApiBean) {
        Pair<User, String> user = authenticationService.createUser(mapper.mapToUser(userCreationRequestApiBean));
        return ResponseEntity.created(UriComponentsBuilder
                                              .fromUriString("/api/v1/users/" + user.getLeft().getId())
                                              .buildAndExpand(user.getLeft().getId())
                                              .toUri())
                             .body(new TwoFactorResponseApiBean().imageBase64(user.getRight()));
    }

    @Override
    public ResponseEntity<UserAuthenticatedResponseApiBean> authenticateUser(UserAuthRequestApiBean user) {
        return ResponseEntity.ok(authenticationService.authenticateUser(mapper.mapToUserAuthenticationRequest(user)));
    }

    @Override
    public ResponseEntity<UserAuthenticatedResponseApiBean> refreshUserToken(String authorization) {
        return ResponseEntity.ok(authenticationService.refreshUserToken(authorization));
    }
}
