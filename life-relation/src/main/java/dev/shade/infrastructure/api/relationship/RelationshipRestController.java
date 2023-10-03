package dev.shade.infrastructure.api.relationship;

import dev.shade.application.model.person.PersonDataVisibility;
import dev.shade.application.model.person.RelationshipRequestApiBean;
import dev.shade.application.model.person.RelationshipStatus;
import dev.shade.application.model.person.SearchRelationshipsApiBean;
import dev.shade.application.model.relationship.RelationshipSearchCriteria;
import dev.shade.application.service.relationship.RelationshipService;
import dev.shade.domain.relation.RelationType;
import dev.shade.domain.relation.Relationship;
import dev.shade.infrastructure.api.person.RelationshipsApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class RelationshipRestController implements RelationshipsApi {

    private final RelationshipService service;
    private final ApiRelationshipMapper mapper;

    @Autowired
    public RelationshipRestController(RelationshipService service, ApiRelationshipMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<SearchRelationshipsApiBean> searchRelationship(UUID personId,
                                                                         Integer pageNumber,
                                                                         Integer pageSize,
                                                                         String firstName,
                                                                         String lastName,
                                                                         List<RelationshipStatus> statuses,
                                                                         List<dev.shade.application.model.person.RelationType> relationtypes,
                                                                         List<PersonDataVisibility> relationVisibilities
    ) {
        RelationshipSearchCriteria.RelationshipSearchCriteriaBuilder builder = RelationshipSearchCriteria.builder();

        if (StringUtils.isNotBlank(firstName)) {
            builder.firstName(firstName);
        }

        if (StringUtils.isNotBlank(lastName)) {
            builder.lastName(lastName);
        }

        if (!CollectionUtils.isEmpty(statuses)) {
            statuses.forEach(it -> builder.status(mapper.mapToRelationshipStatus(it)));
        }

        if (!CollectionUtils.isEmpty(relationtypes)) {
            relationtypes.forEach(it -> builder.relationType(mapper.mapToRelationType(it)));
        }

        if (!CollectionUtils.isEmpty(relationVisibilities)) {
            relationVisibilities.forEach(it -> builder.relationVisibility(mapper.mapToDataRelationType(it)));
        }

        builder.personId(personId);

        return ResponseEntity.ok(mapper.mapToSearchApiBean(service.search(builder.build(), pageNumber, pageSize)));
    }


    @Override
    public ResponseEntity<Void> acceptRelationship(UUID relationshipId) {
        service.acceptRelationshipRequest(relationshipId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> requestRelationship(RelationshipRequestApiBean relationshipRequest) {
        Relationship relationship = service.requestRelationship(relationshipRequest.getRequesterPersonId(),
                relationshipRequest.getReceiverPersonId(),
                RelationType.valueOf(relationshipRequest.getRelationType().name()));
        return ResponseEntity.created(UriComponentsBuilder
                                     .fromUriString("/api/v1/persons/relationships/" + relationship.getId())
                                     .buildAndExpand(relationship.getId())
                                     .toUri())
                             .build();
    }
}