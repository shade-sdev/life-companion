package dev.shade.shared.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND("NOT_FOUND", "Not found");

    private final String code;
    private final String message;
}
