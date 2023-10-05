package dev.shade.infrastructure.api.person;

import dev.shade.application.model.person.*;
import dev.shade.domain.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiPersonMapper {

    @Mapping(target = "firstName", source = "identity.firstName")
    @Mapping(target = "lastName", source = "identity.lastName")
    @Mapping(target = "homeNumber", source = "contact.homeNumber")
    @Mapping(target = "mobileNumber", source = "contact.mobileNumber")
    @Mapping(target = "streetName", source = "address.streetName")
    @Mapping(target = "locality", source = "address.locality")
    @Mapping(target = "houseNumber", source = "address.houseNumber")
    @Mapping(target = "identityVisibility", source = "identity.identityVisibility")
    @Mapping(target = "contactVisibility", source = "contact.contactVisibility")
    @Mapping(target = "addressVisibility", source = "address.addressVisibility")
    PersonRequest mapToRequest(PersonUpdateRequestApiBean personUpdateRequestApiBean);

    @Mapping(target = "auditData.createdBy", source = "auditable.createdBy")
    @Mapping(target = "auditData.lastModifiedBy", source = "auditable.lastModifiedBy")
    @Mapping(target = "auditData.createdDate", source = "auditable.createdDate")
    @Mapping(target = "auditData.lastModifiedDate", source = "auditable.lastModifiedDate")
    PersonApiBean mapToPersonApiBean(Person person);

    @Mapping(target = "firstName", source = "identity.firstName")
    @Mapping(target = "lastName", source = "identity.lastName")
    SearchPersonApiBean mapToSearchPersonApiBean(Person person);

    List<SearchPersonApiBean> mapToSearchPersonsApiBean(List<Person> persons);

    default SearchPersonsApiBean mapToSearchPersonsApiBean(Page<Person> personPage) {
        return new SearchPersonsApiBean()
                .persons(mapToSearchPersonsApiBean(personPage.getContent()))
                .pageSize(personPage.getSize())
                .pageNumber(personPage.getNumber())
                .totalPages(personPage.getTotalPages())
                .totalElements(personPage.getTotalElements());
    }

}
