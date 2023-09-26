package dev.shade.infrastructure.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.shade.shared.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private static final String ISSUER = "life-companion";
    private static final String AUTHORITIES = "authorities";
    private static final String EMAIL = "email";

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${app.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String username(String token) {
        DecodedJWT jwt = decode(token);
        return jwt.getSubject();
    }

    public String generateAccessToken(UserPrincipal userPrincipal, List<String> authorities) {
        return generateToken(userPrincipal, authorities, accessTokenExpiration);
    }

    public String generateRefreshToken(UserPrincipal userPrincipal) {
        return generateToken(userPrincipal, List.of(), refreshTokenExpiration);
    }

    public DecodedJWT decode(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                                  .withIssuer(ISSUER)
                                  .build();
        return verifier.verify(token);
    }

    private String generateToken(UserPrincipal userPrincipal, List<String> authorities, long expiration) {
        return JWT.create()
                  .withIssuer(ISSUER)
                  .withSubject(userPrincipal.getUsername())
                  .withClaim(EMAIL, userPrincipal.getEmail())
                  .withClaim(AUTHORITIES, authorities)
                  .withIssuedAt(new Date(System.currentTimeMillis()))
                  .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                  .sign(Algorithm.HMAC512(secretKey));
    }

}
