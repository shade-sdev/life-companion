package dev.shade.shared.exception;

import dev.shade.shared.exception.model.ErrorCode;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LifeCompanionException extends RuntimeException {

    private final String code;
    private final ErrorCode errorCode;

    public LifeCompanionException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
    }

    public LifeCompanionException(String message, ErrorCode errorCode, Class<?> clazz) {
        super(String.format("%s %s", clazz.getSimpleName(), message));
        this.errorCode = errorCode;
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), errorCode.getCode());
    }

    public LifeCompanionException(String message, ErrorCode errorCode, UUID id, Class<?> clazz) {
        super(String.format("%s (id = %s) %s", clazz.getSimpleName(), id, message));
        this.errorCode = errorCode;
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), errorCode.getCode());
    }

    public LifeCompanionException(String message, ErrorCode errorCode, String idType, UUID id, Class<?> clazz) {
        super(String.format("%s from (%s = %s) %s", clazz.getSimpleName(), idType, id, message));
        this.errorCode = errorCode;
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), errorCode.getCode());
    }
}
