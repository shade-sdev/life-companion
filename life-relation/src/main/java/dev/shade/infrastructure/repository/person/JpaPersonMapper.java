package dev.shade.infrastructure.repository.person;

import dev.shade.domain.person.Person;
import dev.shade.shared.domain.Auditable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JpaPersonMapper {

    @Mapping(target = "auditable", source = ".")
    Person mapToPerson(PersonJpaEntity personJpaEntity);

    @Mapping(target = "createdBy", source = "auditable.createdBy")
    @Mapping(target = "lastModifiedBy", source = "auditable.lastModifiedBy")
    @Mapping(target = "createdDate", source = "auditable.createdDate")
    @Mapping(target = "lastModifiedDate", source = "auditable.lastModifiedDate")
    PersonJpaEntity mapToEntity(Person person);

    default Auditable mapToAuditable(PersonJpaEntity personJpaEntity) {
        return Auditable.builder()
                        .lastModifiedBy(personJpaEntity.getLastModifiedBy())
                        .createdBy(personJpaEntity.getCreatedBy())
                        .createdDate(personJpaEntity.getCreatedDate())
                        .lastModifiedDate(personJpaEntity.getLastModifiedDate())
                        .build();
    }

}
