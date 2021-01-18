package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.DeviceDTO;

import org.mapstruct.*;

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
    Device toEntity(DeviceDTO deviceDTO);

    default Device fromId(Long id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }
}
