package com.voipadmin.service.impl;

import com.voipadmin.domain.Device;
import com.voipadmin.service.impl.configurationprocessor.ConfigurationProcessingStrategyHolder;
import com.voipadmin.service.ConfigurationBuilderService;
import com.voipadmin.service.ConfigurationProcessingStrategy;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

/**
 * Configuration service implementation. Particular device configuration is being built
 * based on device's vendor.
 */
@Service
public class ConfigurationBuilderServiceImpl implements ConfigurationBuilderService {


    private ConfigurationProcessingStrategyHolder processorHolder;

    public ConfigurationBuilderServiceImpl(ConfigurationProcessingStrategyHolder processorHolder) {
        this.processorHolder = processorHolder;
    }

    @Override
    public String buildConfig(Device device) {
        ConfigurationProcessingStrategy processor = processorHolder.get(
            device.getModel().getVendor().getName().toLowerCase()
        );
        if (isNull(processor)) {
            return null;
        }
        return processor.buildConfig(device);
    }
}
