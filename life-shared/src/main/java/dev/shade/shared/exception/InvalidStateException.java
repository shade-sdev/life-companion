package dev.shade.shared.exception;

import dev.shade.shared.exception.model.ErrorCode;
import lombok.Getter;

import java.util.UUID;

@Getter
public class InvalidStateException extends RuntimeException {

    private final String code;

    public InvalidStateException() {
        super(ErrorCode.INVALID_STATE.getMessage());
        this.code = ErrorCode.INVALID_STATE.getCode();
    }

    public InvalidStateException(String message) {
        super(message);
        this.code = ErrorCode.INVALID_STATE.getCode();
    }

    public InvalidStateException(Class<?> clazz) {
        super(String.format("%s is not in a valid state", clazz.getSimpleName()));
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), ErrorCode.INVALID_STATE.getCode());
    }

    public InvalidStateException(UUID id, Class<?> clazz) {
        super(String.format("%s (id = %s) is not in a valid state", clazz.getSimpleName(), id));
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), ErrorCode.INVALID_STATE.getCode());
    }
}