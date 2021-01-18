package com.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.voipadmin.web.rest.TestUtil;

public class AsteriskAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AsteriskAccountDTO.class);
        AsteriskAccountDTO asteriskAccountDTO1 = new AsteriskAccountDTO();
        asteriskAccountDTO1.setId(1L);
        AsteriskAccountDTO asteriskAccountDTO2 = new AsteriskAccountDTO();
        assertThat(asteriskAccountDTO1).isNotEqualTo(asteriskAccountDTO2);
        asteriskAccountDTO2.setId(asteriskAccountDTO1.getId());
        assertThat(asteriskAccountDTO1).isEqualTo(asteriskAccountDTO2);
        asteriskAccountDTO2.setId(2L);
        assertThat(asteriskAccountDTO1).isNotEqualTo(asteriskAccountDTO2);
        asteriskAccountDTO1.setId(null);
        assertThat(asteriskAccountDTO1).isNotEqualTo(asteriskAccountDTO2);
    }
}
