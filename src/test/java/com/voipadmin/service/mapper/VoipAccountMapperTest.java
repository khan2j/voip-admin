package com.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VoipAccountMapperTest {

    private VoipAccountMapper voipAccountMapper;

    @BeforeEach
    public void setUp() {
        voipAccountMapper = new VoipAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(voipAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(voipAccountMapper.fromId(null)).isNull();
    }
}
