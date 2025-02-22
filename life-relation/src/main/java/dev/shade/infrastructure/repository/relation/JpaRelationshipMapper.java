package dev.shade.infrastructure.repository.relation;

import dev.shade.domain.relation.Relationship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JpaRelationshipMapper {

    @Mapping(target = "createdBy", source = "auditable.createdBy")
    @Mapping(target = "lastModifiedBy", source = "auditable.lastModifiedBy")
    @Mapping(target = "createdDate", source = "auditable.createdDate")
    @Mapping(target = "lastModifiedDate", source = "auditable.lastModifiedDate")
    @Mapping(target = "requesterPersonId", source = "requesterPerson.id")
    @Mapping(target = "receiverPersonId", source = "receiverPerson.id")
    @Mapping(target = "requesterPerson", ignore = true)
    @Mapping(target = "receiverPerson", ignore = true)
    RelationshipJpaEntity mapToEntity(Relationship relationship);

    @Mapping(target = "auditable.createdBy", source = "createdBy")
    @Mapping(target = "auditable.lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "auditable.createdDate", source = "createdDate")
    @Mapping(target = "auditable.lastModifiedDate", source = "lastModifiedDate")
    Relationship mapToRelationship(RelationshipJpaEntity relationshipJpaEntity);

    default Page<Relationship> mapToPageRelationship(Page<RelationshipJpaEntity> relationshipJpaEntityPage) {
        return new PageImpl<>(mapToRelationships(relationshipJpaEntityPage.getContent()),
                relationshipJpaEntityPage.getPageable(),
                relationshipJpaEntityPage.getTotalElements());
    }

    List<Relationship> mapToRelationships(List<RelationshipJpaEntity> relationshipJpaEntities);
}
