package dev.shade.domain.relation;

import dev.shade.domain.person.Person;

/**
 * <p>Represents the types of relationships between individuals. This enum includes values for
 * strangers, friends, family members, and special values for data purposes.</p>
 *
 * @author Shade
 * @version 1.0
 * @see Person
 * @see Relationship
 */

public enum RelationType {
    // Relation
    STRANGER,
    FRIEND,
    FAMILY,

    // Data
    NONE,
    ALL;
}