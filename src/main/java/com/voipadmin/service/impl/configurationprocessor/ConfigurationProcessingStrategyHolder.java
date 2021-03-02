package com.voipadmin.service.impl.configurationprocessor;

import com.voipadmin.service.ConfigurationProcessingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Bean is responsible for holding all configuration building strategy implementations.
 */
@Component
public class ConfigurationProcessingStrategyHolder {

    @Autowired
    private Map<String, ConfigurationProcessingStrategy> implementations;

    public ConfigurationProcessingStrategy get(String name) {
        return this.implementations.getOrDefault(name, null);
    }
}
