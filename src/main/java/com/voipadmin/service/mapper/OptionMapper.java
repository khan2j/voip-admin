package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.OptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Option} and its DTO {@link OptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceModelMapper.class})
public interface OptionMapper extends EntityMapper<OptionDTO, Option> {


    @Mapping(target = "possibleValues", ignore = true)
    @Mapping(target = "removePossibleValues", ignore = true)
    @Mapping(target = "removeModels", ignore = true)
    Option toEntity(OptionDTO optionDTO);

    default Option fromId(Long id) {
        if (id == null) {
            return null;
        }
        Option option = new Option();
        option.setId(id);
        return option;
    }
}
