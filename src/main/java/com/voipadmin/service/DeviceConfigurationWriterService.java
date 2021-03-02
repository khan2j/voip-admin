package com.voipadmin.service;

import com.voipadmin.domain.enumeration.ProvisioningMode;

/**
 * Service that is responsible for writing config in particular location for purposes of using FTP/TFTP servers.
 */
public interface DeviceConfigurationWriterService {

    boolean writeConfig(String config, String fileName, ProvisioningMode mode);
}
