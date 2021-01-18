package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.AsteriskAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AsteriskAccount} and its DTO {@link AsteriskAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AsteriskAccountMapper extends EntityMapper<AsteriskAccountDTO, AsteriskAccount> {


    @Mapping(target = "voipAccount", ignore = true)
    AsteriskAccount toEntity(AsteriskAccountDTO asteriskAccountDTO);

    default AsteriskAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        AsteriskAccount asteriskAccount = new AsteriskAccount();
        asteriskAccount.setId(id);
        return asteriskAccount;
    }
}
