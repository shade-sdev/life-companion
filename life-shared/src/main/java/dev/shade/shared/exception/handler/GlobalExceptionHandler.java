package dev.shade.shared.exception.handler;

import dev.shade.shared.exception.InvalidStateException;
import dev.shade.shared.exception.NotFoundException;
import dev.shade.shared.exception.model.GlobalProblemDetail;
import jakarta.validation.ConstraintViolationException;
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
import java.util.Optional;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        return new GlobalProblemDetail(ex.getCode(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStateException.class)
    public ProblemDetail handleInvalidStateException(InvalidStateException ex) {
        return new GlobalProblemDetail(ex.getCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex) {
        return new GlobalProblemDetail("DATA_CONSTRAINT_VIOLATION",
                extractErrorMessage(ex.getMessage()).orElse(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

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
        return handleExceptionInternal(ex,
                null,
                new HttpHeaders(),
                HttpStatusCode.valueOf(500),
                webRequest);
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

    public static Optional<String> extractErrorMessage(String input) {
        if (input.contains(".")) {
            int lastDotIndex = input.lastIndexOf(".");
            return input.substring(lastDotIndex + 1).describeConstable();
        } else {
            return input.describeConstable();
        }
    }
}
