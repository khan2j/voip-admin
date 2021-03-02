package com.voipadmin.service.impl.configurationwriter;

import com.voipadmin.domain.enumeration.ProvisioningMode;
import com.voipadmin.service.DeviceConfigurationWriterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Bean that can write device configuration in particular location on local machine
 * so that phone or gateway can take it later with FTP/TFTP.
 */
public class LocalStorageDeviceConfigurationWriterServiceImpl implements DeviceConfigurationWriterService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalStorageDeviceConfigurationWriterServiceImpl.class);

    @Value("${autoprovisioning.ftp.path:}")
    private String ftpPath;

    @Value("${autoprovisioning.tftp.path:}")
    private String tftpPath;

    @Override
    public boolean writeConfig(String config, String fileName, ProvisioningMode mode) {
        List<String> paths = new ArrayList<>();
        if (nonNull(this.ftpPath)) {
            paths.add(this.ftpPath);
        }
        if (nonNull(this.tftpPath)) {
            paths.add(this.tftpPath);
        }
        paths.forEach(path -> {
            String currentPath = path + File.separator + fileName;
            try {
                Files.write(
                    Paths.get(currentPath), config.getBytes(), StandardOpenOption.TRUNCATE_EXISTING
                );
            } catch (IOException ex) {
                LOG.debug("Error while creating config file, ex: " + ex.getMessage());
            }
        });
        return false;
    }
}
