package dev.shade.shared.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND("NOT_FOUND", "Not found", HttpStatus.NOT_FOUND),
    INVALID_STATE("INVALID_STATE", "Invalid state", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
