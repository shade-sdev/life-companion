package dev.shade.shared.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND("NOT_FOUND", "Not found"),
    INVALID_STATE("INVALID_STATE", "Invalid state");

    private final String code;
    private final String message;
}
