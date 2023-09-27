package dev.shade.shared.exception;

import dev.shade.shared.exception.model.ErrorCode;
import lombok.Getter;

import java.util.UUID;

@Getter
public class InvalidStateException extends LifeCompanionException {

    private static final String DEFAULT_MESSAGE = "is not in a valid state";

    public InvalidStateException() {
        super(ErrorCode.INVALID_STATE.getMessage(), ErrorCode.INVALID_STATE);
    }

    public InvalidStateException(String message) {
        super(message, ErrorCode.INVALID_STATE);
    }

    public InvalidStateException(Class<?> clazz) {
        super(DEFAULT_MESSAGE, ErrorCode.INVALID_STATE, clazz);
    }

    public InvalidStateException(UUID id, Class<?> clazz) {
        super(DEFAULT_MESSAGE, ErrorCode.INVALID_STATE, id, clazz);
    }
}