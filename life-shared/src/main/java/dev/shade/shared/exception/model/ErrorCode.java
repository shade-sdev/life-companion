package dev.shade.shared.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // Client errors
    NOT_FOUND("NOT_FOUND", "Not found", HttpStatus.NOT_FOUND),
    INVALID_STATE("INVALID_STATE", "Invalid state", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("FORBIDDEN", "Access denied", HttpStatus.FORBIDDEN),
    BAD_REQUEST("BAD_REQUEST", "Bad request", HttpStatus.BAD_REQUEST),
    CONFLICT("CONFLICT", "Conflict", HttpStatus.CONFLICT),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation failed", HttpStatus.UNPROCESSABLE_ENTITY),
    DUPLICATE_ENTRY("DUPLICATE_ENTRY", "Duplicate entry", HttpStatus.CONFLICT),

    // Server errors
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    GATEWAY_TIMEOUT("GATEWAY_TIMEOUT", "Gateway timeout", HttpStatus.GATEWAY_TIMEOUT),
    NOT_IMPLEMENTED("NOT_IMPLEMENTED", "Not implemented", HttpStatus.NOT_IMPLEMENTED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
