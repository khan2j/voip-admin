package com.voipadmin.config;

import com.voipadmin.service.DeviceConfigurationWriterService;
import com.voipadmin.service.impl.configurationwriter.LocalStorageDeviceConfigurationWriterServiceImpl;
import com.voipadmin.service.impl.configurationwriter.NoOpDeviceConfigurationWriterServiceImpl;
import com.voipadmin.service.impl.configurationwriter.RemoteStorageDeviceConfigurationWriterServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of writing config files in some location for further use with FTP/TFTP server.
 *
 */
@Configuration
public class DeviceConfigurationWriterConfiguration {

    @Bean
    @ConditionalOnExpression(
        "'${autoprovisioning.ftp.host}'.equals('localhost') || '${autoprovisioning.ftp.host}'.equals('localhost')"
    )
    public DeviceConfigurationWriterService localStorageConfigurationWriterService() {
        return new LocalStorageDeviceConfigurationWriterServiceImpl();
    }

    @Bean
    @ConditionalOnExpression(
        "!'${autoprovisioning.ftp.host}'.equals('localhost') "
        + "&& !'${autoprovisioning.tftp.host}'.equals('localhost')"
    )
    public DeviceConfigurationWriterService remoteStorageConfigurationWriterService() {
        return new RemoteStorageDeviceConfigurationWriterServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DeviceConfigurationWriterService configurationWritingStrategy() {
        return new NoOpDeviceConfigurationWriterServiceImpl();
    }
}
