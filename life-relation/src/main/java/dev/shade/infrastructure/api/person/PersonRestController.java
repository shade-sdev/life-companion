package dev.shade.infrastructure.api.person;

import dev.shade.application.model.person.PersonApiBean;
import dev.shade.application.model.person.PersonSearchCriteria;
import dev.shade.application.model.person.PersonUpdateRequestApiBean;
import dev.shade.application.model.person.SearchPersonsApiBean;
import dev.shade.application.service.person.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
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
    public ResponseEntity<SearchPersonsApiBean> searchPersons(Integer pageNumber, Integer pageSize, String firstName, String lastName) {
        PersonSearchCriteria.PersonSearchCriteriaBuilder builder = PersonSearchCriteria.builder();

        if (StringUtils.isNotBlank(firstName)) {
            builder.firstName(firstName);
        }

        if (StringUtils.isNotBlank(lastName)) {
            builder.firstName(lastName);
        }

        return ResponseEntity.ok(mapper.mapToSearchPersonsApiBean(service.search(builder.build(), pageNumber, pageSize)));
    }

    @Override
    public ResponseEntity<PersonApiBean> getPersonByUserId(UUID userId) {
        Optional<PersonApiBean> personApiBean = service.getPersonByUserId(userId).map(mapper::mapToPersonApiBean);
        return ResponseEntity.of(personApiBean);
    }

    @Override
    public ResponseEntity<Void> updatePerson(UUID personId, PersonUpdateRequestApiBean personUpdateRequestApiBean) {
        service.updatePerson(personId, mapper.mapToRequest(personUpdateRequestApiBean));
        return ResponseEntity.ok().build();
    }
}
