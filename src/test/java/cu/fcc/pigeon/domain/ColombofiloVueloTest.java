package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ColombofiloTestSamples.*;
import static cu.fcc.pigeon.domain.ColombofiloVueloTestSamples.*;
import static cu.fcc.pigeon.domain.VueloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ColombofiloVueloTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColombofiloVuelo.class);
        ColombofiloVuelo colombofiloVuelo1 = getColombofiloVueloSample1();
        ColombofiloVuelo colombofiloVuelo2 = new ColombofiloVuelo();
        assertThat(colombofiloVuelo1).isNotEqualTo(colombofiloVuelo2);

        colombofiloVuelo2.setId(colombofiloVuelo1.getId());
        assertThat(colombofiloVuelo1).isEqualTo(colombofiloVuelo2);

        colombofiloVuelo2 = getColombofiloVueloSample2();
        assertThat(colombofiloVuelo1).isNotEqualTo(colombofiloVuelo2);
    }

    //@Test
    void colombofiloTest() throws Exception {
        ColombofiloVuelo colombofiloVuelo = getColombofiloVueloRandomSampleGenerator();
        Colombofilo colombofiloBack = getColombofiloRandomSampleGenerator();

        colombofiloVuelo.setColombofilo(colombofiloBack);
        assertThat(colombofiloVuelo.getColombofilo()).isEqualTo(colombofiloBack);

        colombofiloVuelo.colombofilo(null);
        assertThat(colombofiloVuelo.getColombofilo()).isNull();
    }

    //@Test
    void vueloTest() throws Exception {
        ColombofiloVuelo colombofiloVuelo = getColombofiloVueloRandomSampleGenerator();
        Vuelo vueloBack = getVueloRandomSampleGenerator();

        colombofiloVuelo.setVuelo(vueloBack);
        assertThat(colombofiloVuelo.getVuelo()).isEqualTo(vueloBack);

        colombofiloVuelo.vuelo(null);
        assertThat(colombofiloVuelo.getVuelo()).isNull();
    }
}
