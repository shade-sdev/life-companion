package dev.shade.infrastructure.api.person;

import dev.shade.application.model.person.PersonUpdateRequestApiBean;
import dev.shade.application.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PersonRestController implements PersonsApi {

    private final PersonService service;

    private final ApiPersonMapper mapper;

    @Autowired
    public PersonRestController(PersonService service, ApiPersonMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> updatePerson(UUID personId, PersonUpdateRequestApiBean personUpdateRequestApiBean) {
        service.updatePerson(personId, mapper.mapToRequest(personUpdateRequestApiBean));
        return ResponseEntity.ok().build();
    }
}
