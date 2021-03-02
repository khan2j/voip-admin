package com.voipadmin.service.impl.configurationprocessor;

import com.voipadmin.domain.Device;
import com.voipadmin.service.ConfigurationProcessingStrategy;
import org.springframework.stereotype.Service;

// TODO implement this strategy
@Service("grandstream")
public class GrandstreamConfigurationProcessingStrategy implements ConfigurationProcessingStrategy {

    private static final String COMMENT_SYMBOL = "# ";

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
