package com.voipadmin.service;

import com.voipadmin.domain.Device;

/**
 * Service that can build config for any configurable device.
 */
public interface ConfigurationBuilderService {

    String buildConfig(Device device);

    default String getFileName(Device device) {
        return device.getMac() + ".cfg";
    }
}
