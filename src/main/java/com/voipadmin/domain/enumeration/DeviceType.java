package com.voipadmin.domain.enumeration;

/**
 * The DeviceType enumeration.
 */
public enum DeviceType {
    IPPHONE("VoIP-Phone"),
    IPGATEWAY("VoIP-Gateway"),
    HANDSET("Handset"),
    HEADSET("Headset"),
    OTHER("Other");

    private String name;

    public String getName() {
        return name;
    }

    DeviceType(String name) {
        this.name = name;
    }
}
