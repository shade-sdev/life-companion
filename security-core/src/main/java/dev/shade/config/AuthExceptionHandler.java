package dev.shade.config;

import dev.shade.shared.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackages = "dev.shade.api")
public class AuthExceptionHandler extends GlobalExceptionHandler {
}
