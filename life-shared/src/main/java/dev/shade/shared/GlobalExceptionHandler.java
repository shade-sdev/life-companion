package dev.shade.shared;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.sql.SQLException;
import java.time.Instant;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String detail = ex.getMessage();
        if (ex.getCause().getCause() instanceof SQLException e && (e.getMessage().contains("Key"))) {
            detail = e.getMessage().substring(e.getMessage().indexOf("Key"));
        }
        return new GlobalProblemDetail("INTEGRITY_CONSTRAINT_VIOLATION", detail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatusCode.valueOf(500), webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex,
                                                             Object body,
                                                             @NonNull HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             @NonNull WebRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(statusCode.value());
        ErrorResponse build = ErrorResponse.builder(ex, status, ex.getMessage())
                                           .detail(ex.getMessage())
                                           .title(status.getReasonPhrase())
                                           .type(URI.create("http://localhost/api/errors/" + status.getReasonPhrase()
                                                                                                   .replace(" ", "-")
                                                                                                   .toLowerCase()))
                                           .property("timestamp", Instant.now())
                                           .build();

        return new ResponseEntity<>(build, status);
    }
}
