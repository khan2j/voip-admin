package com.voipadmin.web.rest;

import com.voipadmin.service.DeviceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Controller for interaction with VoIP-devices.
 */
@RestController
@RequestMapping("/config")
public class ConfigurationResource {

    private final DeviceService deviceService;

    public ConfigurationResource(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    /**
     *
     * Return device configuration based on it's MAC-address.
     * @param mac MAC-address.
     * @return config in text format.
     */
    @GetMapping(value = "/get/{mac}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getConfig(@PathVariable String mac) {
        return deviceService.getConfig(mac);
    }
}
