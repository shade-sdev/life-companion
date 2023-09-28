package dev.shade.application.service.person.impl;

import dev.shade.application.model.person.PersonRequest;
import dev.shade.domain.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "initialized", ignore = true)
    @Mapping(target = "auditable", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identity.firstName", source = "firstName")
    @Mapping(target = "identity.lastName", source = "lastName")
    @Mapping(target = "contact.homeNumber", source = "homeNumber")
    @Mapping(target = "contact.mobileNumber", source = "mobileNumber")
    @Mapping(target = "address.streetName", source = "streetName")
    @Mapping(target = "address.locality", source = "locality")
    @Mapping(target = "address.houseNumber", source = "houseNumber")
    @Mapping(target = "identity.identityVisibility", source = "identityVisibility")
    @Mapping(target = "contact.contactVisibility", source = "contactVisibility")
    @Mapping(target = "address.addressVisibility", source = "addressVisibility")
    Person mapToPerson(PersonRequest personRequest);
}
