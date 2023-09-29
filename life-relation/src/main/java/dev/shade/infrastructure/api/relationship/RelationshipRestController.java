package dev.shade.infrastructure.api.relationship;

import dev.shade.application.model.person.RelationshipRequestApiBean;
import dev.shade.application.service.relationship.RelationshipService;
import dev.shade.domain.relation.RelationType;
import dev.shade.infrastructure.api.person.RelationshipsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class RelationshipRestController implements RelationshipsApi {

    private final RelationshipService service;

    @Autowired
    public RelationshipRestController(RelationshipService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Void> acceptRelationship(UUID relationshipId) {
        service.acceptRelationshipRequest(relationshipId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> requestRelationship(RelationshipRequestApiBean relationshipRequest) {
        service.requestRelationship(relationshipRequest.getRequesterPersonId(),
                relationshipRequest.getReceiverPersonId(),
                RelationType.valueOf(relationshipRequest.getRelationType().name()));
        return ResponseEntity.ok().build();
    }
}