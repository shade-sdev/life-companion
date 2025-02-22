package dev.shade.infrastructure.config;

import dev.shade.shared.exception.handler.GlobalExceptionHandler;
import dev.shade.shared.exception.model.GlobalProblemDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "dev.shade.api")
public class AuthExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException ex) {
        return new GlobalProblemDetail("BAD_CREDENTIAL", ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
