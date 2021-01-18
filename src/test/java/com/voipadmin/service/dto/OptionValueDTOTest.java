package com.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.voipadmin.web.rest.TestUtil;

public class OptionValueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionValueDTO.class);
        OptionValueDTO optionValueDTO1 = new OptionValueDTO();
        optionValueDTO1.setId(1L);
        OptionValueDTO optionValueDTO2 = new OptionValueDTO();
        assertThat(optionValueDTO1).isNotEqualTo(optionValueDTO2);
        optionValueDTO2.setId(optionValueDTO1.getId());
        assertThat(optionValueDTO1).isEqualTo(optionValueDTO2);
        optionValueDTO2.setId(2L);
        assertThat(optionValueDTO1).isNotEqualTo(optionValueDTO2);
        optionValueDTO1.setId(null);
        assertThat(optionValueDTO1).isNotEqualTo(optionValueDTO2);
    }
}
