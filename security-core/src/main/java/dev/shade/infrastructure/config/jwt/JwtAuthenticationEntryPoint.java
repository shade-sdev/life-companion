package dev.shade.infrastructure.config.jwt;

import dev.shade.application.model.problemdetail.SecurityProblemDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        new SecurityProblemDetail().response(request, response, authException);
    }
}
