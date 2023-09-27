package dev.shade.infrastructure.api.person;

import dev.shade.application.model.person.PersonRequest;
import dev.shade.application.model.person.PersonUpdateRequestApiBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

}
