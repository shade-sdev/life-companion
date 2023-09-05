package dev.shade.config.jwt;

import dev.shade.model.SecurityProblemDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Objects;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String token = Optional.ofNullable(authorizationHeader)
                                     .filter(header -> header.startsWith(BEARER))
                                     .map(header -> header.replace(BEARER, ""))
                                     .orElse(null);

        try {
            if (Objects.isNull(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            Optional.of(token)
                    .map(jwtService::username)
                    .filter(it -> Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
                    .map(userDetailsService::loadUserByUsername)
                    .filter(UserDetails::isAccountNonLocked)
                    .ifPresent(it -> {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(it, it.getPassword(), it.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            new SecurityProblemDetail().response(request, response, e);
        }

    }
}
