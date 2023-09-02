package dev.shade.service.impl;

import dev.shade.config.JwtService;
import dev.shade.domain.user.User;
import dev.shade.model.UserAuthenticatedResponseApiBean;
import dev.shade.model.UserPrincipal;
import dev.shade.service.AuthenticationService;
import dev.shade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        User createUser = user.toBuilder()
                              .password(passwordEncoder.encode(user.getPassword()))
                              .build();

        return userService.createUser(createUser);

    }

    @Override
    @Transactional(readOnly = true)
    public UserAuthenticatedResponseApiBean authenticateUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userService.findBy(username)
                          .map(it -> UserPrincipal.builder()
                                                  .user(it)
                                                  .build())
                          .map(jwtService::generateToken)
                          .map(it -> {
                              UserAuthenticatedResponseApiBean userResponse = new UserAuthenticatedResponseApiBean();
                              userResponse.setToken(it);
                              return userResponse;
                          })
                          .orElseThrow();
    }

}
