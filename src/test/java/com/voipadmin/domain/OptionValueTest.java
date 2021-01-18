package com.voipadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.voipadmin.web.rest.TestUtil;

public class OptionValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionValue.class);
        OptionValue optionValue1 = new OptionValue();
        optionValue1.setId(1L);
        OptionValue optionValue2 = new OptionValue();
        optionValue2.setId(optionValue1.getId());
        assertThat(optionValue1).isEqualTo(optionValue2);
        optionValue2.setId(2L);
        assertThat(optionValue1).isNotEqualTo(optionValue2);
        optionValue1.setId(null);
        assertThat(optionValue1).isNotEqualTo(optionValue2);
    }
}
