package dev.shade.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.shade.config.jwt.JwtService;
import dev.shade.config.totp.TwoFactorAuthenticationService;
import dev.shade.domain.user.User;
import dev.shade.model.UserAuthenticatedResponseApiBean;
import dev.shade.service.AuthenticationService;
import dev.shade.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String BEARER = "Bearer ";

    private final UserService userService;
    private final JwtService jwtService;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;
    private final SecurityMapper mapper;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     JwtService jwtService,
                                     TwoFactorAuthenticationService twoFactorAuthenticationService, SecurityMapper mapper,
                                     AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder
                                    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Pair<User, String> createUser(@NotNull @Valid User user) {
        String secretKey = twoFactorAuthenticationService.generateSecret().getBase32Encoded();
        String twoFactorQrCode = Optional.of(user)
                                         .map(User::getSecurity)
                                         .filter(User.Security::isTwoFactorEnabled)
                                         .map(it -> twoFactorAuthenticationService.generateQRCodeImage(secretKey))
                                         .orElse(StringUtils.EMPTY);
        String password = Optional.of(user).map(User::getSecurity).map(User.Security::getPassword).orElseThrow();
        User createUser = user.initializeSecurity(
                passwordEncoder.encode(password), secretKey);
        return Pair.of(userService.createUser(createUser), twoFactorQrCode);
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
