package com.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionValueMapperTest {

    private OptionValueMapper optionValueMapper;

    @BeforeEach
    public void setUp() {
        optionValueMapper = new OptionValueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(optionValueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(optionValueMapper.fromId(null)).isNull();
    }
}
