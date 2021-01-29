package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.DeviceModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceModel} and its DTO {@link DeviceModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {OtherDeviceTypeMapper.class, OptionMapper.class})
public interface DeviceModelMapper extends EntityMapper<DeviceModelDTO, DeviceModel> {

    @Mapping(source = "otherDeviceType.id", target = "otherDeviceTypeId")
    @Mapping(source = "otherDeviceType.name", target = "otherDeviceTypeName")
    DeviceModelDTO toDto(DeviceModel deviceModel);

    @Mapping(source = "otherDeviceTypeId", target = "otherDeviceType")
    @Mapping(target = "removeOptions", ignore = true)
    DeviceModel toEntity(DeviceModelDTO deviceModelDTO);

    default DeviceModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setId(id);
        return deviceModel;
    }
}
