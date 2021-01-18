package com.voipadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.voipadmin.web.rest.TestUtil;

public class OtherDeviceTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtherDeviceType.class);
        OtherDeviceType otherDeviceType1 = new OtherDeviceType();
        otherDeviceType1.setId(1L);
        OtherDeviceType otherDeviceType2 = new OtherDeviceType();
        otherDeviceType2.setId(otherDeviceType1.getId());
        assertThat(otherDeviceType1).isEqualTo(otherDeviceType2);
        otherDeviceType2.setId(2L);
        assertThat(otherDeviceType1).isNotEqualTo(otherDeviceType2);
        otherDeviceType1.setId(null);
        assertThat(otherDeviceType1).isNotEqualTo(otherDeviceType2);
    }
}
