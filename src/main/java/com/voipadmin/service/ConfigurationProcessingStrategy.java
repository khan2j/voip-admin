package com.voipadmin.service;

import com.voipadmin.domain.Device;

/**
 *
 * Service that is responsible for building configuration for particular vendor.
 * Strategy must contain name of vendor in component annotation value for autohold.
 */
public interface ConfigurationProcessingStrategy {

    public static final String LINE_SEPARATOR = "\r\n";

    String buildConfig(Device device);

    void processGeneralSettings(Device device);

    void processLineSettings(Device device);

    void processAdditionalSettings(Device device);
}
