package dev.shade.infrastructure.configuration;

import dev.shade.shared.exception.handler.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackages = "dev.shade.infrastructure.api")
public class PersonEntityExceptionHandler extends GlobalExceptionHandler {
}
