package com.voipadmin.service.impl.configurationprocessor;

import com.voipadmin.domain.Device;
import com.voipadmin.service.ConfigurationProcessingStrategy;
import org.springframework.stereotype.Service;

// TODO implement this strategy
@Service("dlink")
public class DlinkConfigurationProcessingStrategy implements ConfigurationProcessingStrategy {

    @Override
    public String buildConfig(Device device) {
        return null;
    }

    @Override
    public void processGeneralSettings(Device device) {

    }

    @Override
    public void processLineSettings(Device device) {

    }

    @Override
    public void processAdditionalSettings(Device device) {

    }
}
