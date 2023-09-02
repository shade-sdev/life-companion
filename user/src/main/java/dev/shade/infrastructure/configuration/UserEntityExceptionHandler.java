package dev.shade;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;

@ControllerAdvice(basePackages = "dev.shade.infrastructure.api")
public class UserEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleTestException(Exception ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, null, null, HttpStatusCode.valueOf(500), webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        HttpStatus status = HttpStatus.valueOf(statusCode.value());
        ErrorResponse build = ErrorResponse.builder(ex, status, ex.getMessage())
                .detail(ex.getMessage())
                .title(status.getReasonPhrase())
                .type(URI.create("http://localhost/api/errors/" + status.getReasonPhrase().replace(" ", "-").toLowerCase()))
                .property("timestamp", Instant.now())
                .build();

        return new ResponseEntity<>(build, status);
    }

}
