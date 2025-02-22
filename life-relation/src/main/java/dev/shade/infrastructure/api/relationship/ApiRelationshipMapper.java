package dev.shade.infrastructure.api.relationship;

import dev.shade.application.model.person.PersonDataVisibility;
import dev.shade.application.model.person.RelationshipApiBean;
import dev.shade.application.model.person.SearchRelationshipsApiBean;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.domain.relation.RelationshipStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiRelationshipMapper {

    RelationshipStatus mapToRelationshipStatus(dev.shade.application.model.person.RelationshipStatus status);

    RelationType mapToRelationType(dev.shade.application.model.person.RelationType relationType);

    RelationType mapToDataRelationType(PersonDataVisibility personDataVisibility);

    default SearchRelationshipsApiBean mapToSearchApiBean(Page<Relationship> relationshipPage) {
        return new SearchRelationshipsApiBean()
                .relationships(mapToRelationshipApiBean(relationshipPage.getContent()))
                .pageNumber(relationshipPage.getNumber())
                .pageSize(relationshipPage.getSize())
                .totalElements(relationshipPage.getTotalElements())
                .totalPages(relationshipPage.getTotalPages());
    }

    List<RelationshipApiBean> mapToRelationshipApiBean(List<Relationship> relationships);

    @Mapping(target = "relationtype", source = "relationType")
    @Mapping(target = "auditData.createdBy", source = "auditable.createdBy")
    @Mapping(target = "auditData.lastModifiedBy", source = "auditable.lastModifiedBy")
    @Mapping(target = "auditData.createdDate", source = "auditable.createdDate")
    @Mapping(target = "auditData.lastModifiedDate", source = "auditable.lastModifiedDate")
    @Mapping(target = "requesterPerson.relationVisibility", source = "requesterRelationVisibility")
    @Mapping(target = "receiverPerson.relationVisibility", source = "receiverRelationVisibility")
    @Mapping(target = "requesterPerson.id", source = "requesterPerson.id")
    @Mapping(target = "requesterPerson.firstName", source = "requesterPerson.firstName")
    @Mapping(target = "requesterPerson.lastName", source = "requesterPerson.lastName")
    @Mapping(target = "requesterPerson.userId", source = "requesterPerson.userId")
    @Mapping(target = "receiverPerson.id", source = "receiverPerson.id")
    @Mapping(target = "receiverPerson.firstName", source = "receiverPerson.firstName")
    @Mapping(target = "receiverPerson.lastName", source = "receiverPerson.lastName")
    @Mapping(target = "receiverPerson.userId", source = "receiverPerson.userId")
    RelationshipApiBean mapToRelationshipApiBean(Relationship relationship);

}
