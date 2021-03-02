package com.voipadmin.service.impl.configurationwriter;

import com.voipadmin.domain.enumeration.ProvisioningMode;
import com.voipadmin.service.DeviceConfigurationWriterService;
import org.springframework.beans.factory.annotation.Value;

/**
 * Bean that can write device configuration in particular location on remote machine
 * so that phone or gateway can take it later with FTP/TFTP.
 */
public class RemoteStorageDeviceConfigurationWriterServiceImpl implements DeviceConfigurationWriterService {

    @Value("${autoprovisioning.ftp.path}")
    private String ftpPath;

    @Value("${autoprovisioning.tftp.path}")
    private String tftpPath;

    @Override
    public boolean writeConfig(String config, String fileName, ProvisioningMode mode) {
        // TODO implement connection to remote server based on it's address in external config.
        return false;
    }
}
