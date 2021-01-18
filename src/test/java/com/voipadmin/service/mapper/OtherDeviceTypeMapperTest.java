package com.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OtherDeviceTypeMapperTest {

    private OtherDeviceTypeMapper otherDeviceTypeMapper;

    @BeforeEach
    public void setUp() {
        otherDeviceTypeMapper = new OtherDeviceTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(otherDeviceTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(otherDeviceTypeMapper.fromId(null)).isNull();
    }
}
