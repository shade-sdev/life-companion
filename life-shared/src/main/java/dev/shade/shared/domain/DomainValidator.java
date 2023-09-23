package dev.shade.shared.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

public class DomainValidator<T> {

    public void validate(T domainObject, Validator validator, Class<?>[] groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(domainObject, groups);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                                            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                            .collect(Collectors.joining(", "));
            throw new ConstraintViolationException(errorMessage, violations);
        }
    }

}
