package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.PalomaTestSamples.*;
import static cu.fcc.pigeon.domain.PremioTestSamples.*;
import static cu.fcc.pigeon.domain.VueloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PremioTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Premio.class);
        Premio premio1 = getPremioSample1();
        Premio premio2 = new Premio();
        assertThat(premio1).isNotEqualTo(premio2);

        premio2.setId(premio1.getId());
        assertThat(premio1).isEqualTo(premio2);

        premio2 = getPremioSample2();
        assertThat(premio1).isNotEqualTo(premio2);
    }

    //@Test
    void palomaTest() throws Exception {
        Premio premio = getPremioRandomSampleGenerator();
        Paloma palomaBack = getPalomaRandomSampleGenerator();

        premio.setPaloma(palomaBack);
        assertThat(premio.getPaloma()).isEqualTo(palomaBack);

        premio.paloma(null);
        assertThat(premio.getPaloma()).isNull();
    }

    //@Test
    void vueloTest() throws Exception {
        Premio premio = getPremioRandomSampleGenerator();
        Vuelo vueloBack = getVueloRandomSampleGenerator();

        premio.setVuelo(vueloBack);
        assertThat(premio.getVuelo()).isEqualTo(vueloBack);

        premio.vuelo(null);
        assertThat(premio.getVuelo()).isNull();
    }
}
