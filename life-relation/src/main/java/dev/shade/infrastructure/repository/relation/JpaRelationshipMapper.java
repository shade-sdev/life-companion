package dev.shade.infrastructure.repository.relation;

import dev.shade.domain.relation.Relationship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JpaRelationshipMapper {

    @Mapping(target = "createdBy", source = "auditable.createdBy")
    @Mapping(target = "lastModifiedBy", source = "auditable.lastModifiedBy")
    @Mapping(target = "createdDate", source = "auditable.createdDate")
    @Mapping(target = "lastModifiedDate", source = "auditable.lastModifiedDate")
    RelationshipJpaEntity mapToEntity(Relationship relationship);

    @Mapping(target = "auditable.createdBy", source = "createdBy")
    @Mapping(target = "auditable.lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "auditable.createdDate", source = "createdDate")
    @Mapping(target = "auditable.lastModifiedDate", source = "lastModifiedDate")
    Relationship mapToRelationship(RelationshipJpaEntity relationshipJpaEntity);
}
