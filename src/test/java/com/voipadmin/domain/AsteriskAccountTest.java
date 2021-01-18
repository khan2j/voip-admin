package com.voipadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.voipadmin.web.rest.TestUtil;

public class AsteriskAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AsteriskAccount.class);
        AsteriskAccount asteriskAccount1 = new AsteriskAccount();
        asteriskAccount1.setId(1L);
        AsteriskAccount asteriskAccount2 = new AsteriskAccount();
        asteriskAccount2.setId(asteriskAccount1.getId());
        assertThat(asteriskAccount1).isEqualTo(asteriskAccount2);
        asteriskAccount2.setId(2L);
        assertThat(asteriskAccount1).isNotEqualTo(asteriskAccount2);
        asteriskAccount1.setId(null);
        assertThat(asteriskAccount1).isNotEqualTo(asteriskAccount2);
    }
}
