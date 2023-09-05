package dev.shade.shared.exception;

import dev.shade.shared.exception.model.ErrorCode;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;

    public NotFoundException() {
        super(ErrorCode.NOT_FOUND.getMessage());
        this.code = ErrorCode.NOT_FOUND.getCode();
    }

    public NotFoundException(String message) {
        super(message);
        this.code = ErrorCode.NOT_FOUND.getCode();
    }

    public NotFoundException(Class<?> clazz) {
        super(String.format("%s not found", clazz.getSimpleName()));
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), ErrorCode.NOT_FOUND.getCode());
    }

    public NotFoundException(UUID id, Class<?> clazz) {
        super(String.format("%s (id = %s) not found", clazz.getSimpleName(), id));
        this.code = String.format("%s_%s", clazz.getSimpleName().toUpperCase(), ErrorCode.NOT_FOUND.getCode());
    }

}

