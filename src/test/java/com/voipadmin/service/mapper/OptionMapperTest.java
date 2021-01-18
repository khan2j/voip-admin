package com.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionMapperTest {

    private OptionMapper optionMapper;

    @BeforeEach
    public void setUp() {
        optionMapper = new OptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(optionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(optionMapper.fromId(null)).isNull();
    }
}
