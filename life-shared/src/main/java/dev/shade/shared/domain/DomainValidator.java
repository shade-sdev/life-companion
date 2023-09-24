package dev.shade.shared.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>A utility class for validating domain objects using Jakarta Validation API (Bean Validation).
 * It checks for constraint violations and throws an exception if any are found.</p>
 *
 * @param <T> The type of the domain object to be validated.
 * @author Shade
 * @version 1.0
 */
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
