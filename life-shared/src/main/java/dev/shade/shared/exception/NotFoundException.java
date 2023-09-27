package dev.shade.shared.exception;

import dev.shade.shared.exception.model.ErrorCode;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends LifeCompanionException {

    private static final String DEFAULT_MESSAGE = "not found";

    public NotFoundException() {
        super(ErrorCode.NOT_FOUND.getMessage(), ErrorCode.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }

    public NotFoundException(Class<?> clazz) {
        super(DEFAULT_MESSAGE, ErrorCode.NOT_FOUND, clazz);
    }

    public NotFoundException(UUID id, Class<?> clazz) {
        super(DEFAULT_MESSAGE, ErrorCode.NOT_FOUND, id, clazz);
    }

}

