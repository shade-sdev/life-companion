package dev.shade.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.shade.shared.security.permissionevalutor.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String ISSUER = "life-companion";
    private static final String AUTHORITIES = "authorities";

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private long validityInMilliseconds;

    public String username(String token) {
        DecodedJWT jwt = decode(token);
        return jwt.getSubject();
    }

    public String generateToken(UserPrincipal userPrincipal) {
        return JWT.create()
                  .withIssuer(ISSUER)
                  .withSubject(userPrincipal.getUsername())
                  .withArrayClaim(AUTHORITIES, userPrincipal.getAuthorities()
                                                              .stream()
                                                              .map(GrantedAuthority::getAuthority)
                                                              .toArray(String[]::new))
                  .withIssuedAt(new Date(System.currentTimeMillis()))
                  .withExpiresAt(new Date(System.currentTimeMillis() + validityInMilliseconds))
                  .sign(Algorithm.HMAC512(secretKey));
    }

    private DecodedJWT decode(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                                  .withIssuer(ISSUER)
                                  .build();
        return verifier.verify(token);
    }

}
