package com.voipadmin.service.mapper;


import com.voipadmin.domain.*;
import com.voipadmin.service.dto.VendorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendor} and its DTO {@link VendorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VendorMapper extends EntityMapper<VendorDTO, Vendor> {


    @Mapping(target = "deviceModels", ignore = true)
    @Mapping(target = "removeDeviceModels", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "removeOptions", ignore = true)
    Vendor toEntity(VendorDTO vendorDTO);

    default Vendor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vendor vendor = new Vendor();
        vendor.setId(id);
        return vendor;
    }
}
