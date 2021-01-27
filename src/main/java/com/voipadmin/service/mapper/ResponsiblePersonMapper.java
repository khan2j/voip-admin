package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.DeviceDTO;
import com.voipadmin.service.dto.ResponsiblePersonDTO;

import org.mapstruct.*;

import java.text.MessageFormat;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static liquibase.util.StringUtils.isNotEmpty;

/**
 * Mapper for the entity {@link ResponsiblePerson} and its DTO {@link ResponsiblePersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface ResponsiblePersonMapper extends EntityMapper<ResponsiblePersonDTO, ResponsiblePerson> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    ResponsiblePersonDTO toDto(ResponsiblePerson responsiblePerson);

    @Mapping(source = "departmentId", target = "department")
    ResponsiblePerson toEntity(ResponsiblePersonDTO responsiblePersonDTO);

    default ResponsiblePerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResponsiblePerson responsiblePerson = new ResponsiblePerson();
        responsiblePerson.setId(id);
        return responsiblePerson;
    }

    @AfterMapping
    default void setResponsiblePersonFullName(@MappingTarget ResponsiblePersonDTO dto, ResponsiblePerson entity) {
        dto.setFullName(MessageFormat.format(
            "{0} {1}{2}",
            entity.getLastName(),
            isNotEmpty(entity.getFirstName()) ? entity.getFirstName().substring(0, 1) + "." : "",
            isNotEmpty(entity.getFirstName()) && isNotEmpty(entity.getSecondName()) ?
                entity.getSecondName().substring(0, 1) + "." : ""
        ));
    }
}
