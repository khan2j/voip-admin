package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.SettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Setting} and its DTO {@link SettingDTO}.
 */
@Mapper(componentModel = "spring", uses = {OptionMapper.class, OptionValueMapper.class, DeviceMapper.class})
public interface SettingMapper extends EntityMapper<SettingDTO, Setting> {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "device.mac", target = "deviceMac")
    SettingDTO toDto(Setting setting);

    @Mapping(target = "removeSelectedValues", ignore = true)
    @Mapping(source = "deviceId", target = "device")
    Setting toEntity(SettingDTO settingDTO);

    default Setting fromId(Long id) {
        if (id == null) {
            return null;
        }
        Setting setting = new Setting();
        setting.setId(id);
        return setting;
    }
}
