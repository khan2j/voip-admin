package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.DeviceDTO;

import org.mapstruct.*;

import java.text.MessageFormat;

import static java.util.Objects.isNull;
import static liquibase.util.StringUtils.isNotEmpty;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceModelMapper.class, ResponsiblePersonMapper.class})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.name", target = "modelName")
    @Mapping(source = "responsiblePerson.id", target = "responsiblePersonId")
    @Mapping(source = "responsiblePerson.lastName", target = "responsiblePersonLastName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "mac", target = "mac", qualifiedByName = "plainMacToFormatted")
    DeviceDTO toDto(Device device);

    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "removeSettings", ignore = true)
    @Mapping(target = "voipAccounts", ignore = true)
    @Mapping(target = "removeVoipAccounts", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(source = "modelId", target = "model")
    @Mapping(source = "responsiblePersonId", target = "responsiblePerson")
    @Mapping(source = "parentId", target = "parent")
    @Mapping(source = "mac", target = "mac", qualifiedByName = "formattedMacToPlain")
    Device toEntity(DeviceDTO deviceDTO);

    default Device fromId(Long id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }

    @Named("plainMacToFormatted")
    public static String plainMacToFormatted(String plainMac) {
        if (plainMac.length() != 12) {
            return plainMac;
        }
        String[] octets = plainMac.split("(?<=\\G..)");
        return String.join(":", octets);
    }

    @Named("formattedMacToPlain")
    public static String formattedMacToPlain(String formattedMac) {
        return formattedMac.replace(":", "").replace("-", "");
    }

    @AfterMapping
    default void setResponsiblePersonFullName(@MappingTarget DeviceDTO dto, Device entity) {
        ResponsiblePerson responsiblePerson = entity.getResponsiblePerson();
        if (isNull(responsiblePerson)) {
            return;
        }
        dto.setResponsiblePersonFullName(MessageFormat.format(
            "{0} {1}{2}",
            responsiblePerson.getLastName(),
            isNotEmpty(responsiblePerson.getFirstName()) ? responsiblePerson.getFirstName().substring(0, 1) + "." : "",
            isNotEmpty(responsiblePerson.getFirstName()) && isNotEmpty(responsiblePerson.getSecondName()) ?
                responsiblePerson.getSecondName().substring(0, 1) + "." : ""
        ));
    }
}
