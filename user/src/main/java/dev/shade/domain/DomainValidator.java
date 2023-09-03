package dev.shade.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

public class DomainValidator<T> {

    public void validate(T domainObject, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(domainObject);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                                            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                            .collect(Collectors.joining(", "));
            throw new ConstraintViolationException(errorMessage, violations);
        }
    }

}
