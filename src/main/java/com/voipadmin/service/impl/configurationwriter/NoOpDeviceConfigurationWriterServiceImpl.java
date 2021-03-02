package com.voipadmin.service.impl.configurationwriter;

import com.voipadmin.domain.enumeration.ProvisioningMode;
import com.voipadmin.service.DeviceConfigurationWriterService;

/**
 * Bean that is used in case of missing LocalStorage or RemoteStorage writer services.
 */
public class NoOpDeviceConfigurationWriterServiceImpl implements DeviceConfigurationWriterService {

    @Override
    public boolean writeConfig(String config, String fileName, ProvisioningMode mode) {
        return false;
    }
}
