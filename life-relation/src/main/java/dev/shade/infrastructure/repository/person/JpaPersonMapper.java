package dev.shade.infrastructure.repository.person;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import dev.shade.domain.person.Person;
import dev.shade.shared.domain.Auditable;

@Mapper(componentModel = "spring")
public interface JpaPersonMapper {

    @Mapping(target = "auditable", source = ".")
    Person mapToPerson(PersonJpaEntity personJpaEntity);

    @Mapping(target = "createdBy", source = "auditable.createdBy")
    @Mapping(target = "lastModifiedBy", source = "auditable.lastModifiedBy")
    @Mapping(target = "createdDate", source = "auditable.createdDate")
    @Mapping(target = "lastModifiedDate", source = "auditable.lastModifiedDate")
    PersonJpaEntity mapToEntity(Person person);

    List<Person> mapToPersons(List<PersonJpaEntity> personJpaEntities);

    default Auditable mapToAuditable(PersonJpaEntity personJpaEntity) {
        return Auditable.builder()
                        .lastModifiedBy(personJpaEntity.getLastModifiedBy())
                        .createdBy(personJpaEntity.getCreatedBy())
                        .createdDate(personJpaEntity.getCreatedDate())
                        .lastModifiedDate(personJpaEntity.getLastModifiedDate())
                        .build();
    }

    default Page<Person> mapToPersonPage(Page<PersonJpaEntity> personJpaEntityPage) {
        return new PageImpl<>(mapToPersons(personJpaEntityPage.getContent()),
                              personJpaEntityPage.getPageable(),
                              personJpaEntityPage.getTotalElements());
    }

}
