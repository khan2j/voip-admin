package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.OtherDeviceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OtherDeviceType} and its DTO {@link OtherDeviceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OtherDeviceTypeMapper extends EntityMapper<OtherDeviceTypeDTO, OtherDeviceType> {



    default OtherDeviceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        OtherDeviceType otherDeviceType = new OtherDeviceType();
        otherDeviceType.setId(id);
        return otherDeviceType;
    }
}
