package dev.shade.domain.relation;

import dev.shade.domain.person.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>Represents the types of relationships between individuals. This enum includes values for
 * strangers, friends, family members, and special values for data purposes.</p>
 *
 * @author Shade
 * @version 1.0
 * @see Person
 * @see Relationship
 */
@RequiredArgsConstructor
@Getter
public enum RelationType {
    // Relation
    FAMILY(4),
    FRIEND(3),
    STRANGER(2),

    // Data
    ALL(1),
    NONE(0);

    private final int level;
}