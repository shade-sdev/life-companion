package dev.shade.infrastructure.configuration;

import dev.shade.shared.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackages = "dev.shade.infrastructure.api")
public class UserEntityExceptionHandler extends GlobalExceptionHandler {
}
