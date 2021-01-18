package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.ResponsiblePersonDTO;

import org.mapstruct.*;

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
}
