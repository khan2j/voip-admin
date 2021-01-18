package com.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AsteriskAccountMapperTest {

    private AsteriskAccountMapper asteriskAccountMapper;

    @BeforeEach
    public void setUp() {
        asteriskAccountMapper = new AsteriskAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(asteriskAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(asteriskAccountMapper.fromId(null)).isNull();
    }
}
