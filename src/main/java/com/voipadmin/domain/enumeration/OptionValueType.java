package com.voipadmin.domain.enumeration;

/**
 * The OptionValueType enumeration.
 */
public enum OptionValueType {
    TEXT("Arbitrary"), SELECT("Predefined");

    private String name;

    public String getName() {
        return name;
    }

    OptionValueType(String name) {
        this.name = name;
    }
}
