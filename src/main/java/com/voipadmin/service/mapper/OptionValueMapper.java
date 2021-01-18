package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.OptionValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OptionValue} and its DTO {@link OptionValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {OptionMapper.class})
public interface OptionValueMapper extends EntityMapper<OptionValueDTO, OptionValue> {

    @Mapping(source = "option.id", target = "optionId")
    OptionValueDTO toDto(OptionValue optionValue);

    @Mapping(source = "optionId", target = "option")
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "removeSettings", ignore = true)
    OptionValue toEntity(OptionValueDTO optionValueDTO);

    default OptionValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        OptionValue optionValue = new OptionValue();
        optionValue.setId(id);
        return optionValue;
    }
}
