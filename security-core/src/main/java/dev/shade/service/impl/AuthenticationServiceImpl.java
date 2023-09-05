package dev.shade.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.shade.config.JwtService;
import dev.shade.domain.user.User;
import dev.shade.model.UserAuthenticatedResponseApiBean;
import dev.shade.service.AuthenticationService;
import dev.shade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String BEARER = "Bearer ";

    private final UserService userService;
    private final JwtService jwtService;
    private final SecurityMapper mapper;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     JwtService jwtService,
                                     SecurityMapper mapper,
                                     AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder
                                    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.mapper = mapper;
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
    public UserAuthenticatedResponseApiBean authenticateUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return userService.findBy(username)
                          .map(mapper::mapToUserPrincipal)
                          .map(it -> {
                              UserAuthenticatedResponseApiBean userResponse = new UserAuthenticatedResponseApiBean();
                              userResponse.setToken(jwtService.generateAccessToken(it, it.getAuthoritiesList()));
                              userResponse.setRefreshToken(jwtService.generateRefreshToken(it));
                              return userResponse;
                          })
                          .orElseThrow();
    }

    @Override
    public UserAuthenticatedResponseApiBean refreshUserToken(String authorization) {
        final String token = Optional.ofNullable(authorization)
                                     .filter(header -> header.startsWith(BEARER))
                                     .map(header -> header.replace(BEARER, ""))
                                     .orElse(null);

        DecodedJWT decodedToken = jwtService.decode(token);

        return Optional.ofNullable(decodedToken)
                       .map(DecodedJWT::getSubject)
                       .flatMap(userService::findBy)
                       .map(mapper::mapToUserPrincipal)
                       .map(it -> {
                           UserAuthenticatedResponseApiBean userResponse = new UserAuthenticatedResponseApiBean();
                           userResponse.setToken(jwtService.generateAccessToken(it, it.getAuthoritiesList()));
                           userResponse.setRefreshToken(jwtService.generateRefreshToken(it));
                           return userResponse;
                       })
                       .orElseThrow();
    }

}
