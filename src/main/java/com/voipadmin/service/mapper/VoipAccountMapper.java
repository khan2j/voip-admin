package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.VoipAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoipAccount} and its DTO {@link VoipAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {AsteriskAccountMapper.class, DeviceMapper.class})
public interface VoipAccountMapper extends EntityMapper<VoipAccountDTO, VoipAccount> {

    @Mapping(source = "asteriskAccount.id", target = "asteriskAccountId")
    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "device.mac", target = "deviceMac")
    VoipAccountDTO toDto(VoipAccount voipAccount);

    @Mapping(source = "asteriskAccountId", target = "asteriskAccount")
    @Mapping(source = "deviceId", target = "device")
    VoipAccount toEntity(VoipAccountDTO voipAccountDTO);

    default VoipAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoipAccount voipAccount = new VoipAccount();
        voipAccount.setId(id);
        return voipAccount;
    }
}
