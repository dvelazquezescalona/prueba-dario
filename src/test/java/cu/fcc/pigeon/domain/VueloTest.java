package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ColombofiloVueloTestSamples.*;
import static cu.fcc.pigeon.domain.ParaderoTestSamples.*;
import static cu.fcc.pigeon.domain.PremioTestSamples.*;
import static cu.fcc.pigeon.domain.VueloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VueloTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vuelo.class);
        Vuelo vuelo1 = getVueloSample1();
        Vuelo vuelo2 = new Vuelo();
        assertThat(vuelo1).isNotEqualTo(vuelo2);

        vuelo2.setId(vuelo1.getId());
        assertThat(vuelo1).isEqualTo(vuelo2);

        vuelo2 = getVueloSample2();
        assertThat(vuelo1).isNotEqualTo(vuelo2);
    }

    //@Test
    void colombofiloVueloTest() throws Exception {
        Vuelo vuelo = getVueloRandomSampleGenerator();
        ColombofiloVuelo colombofiloVueloBack = getColombofiloVueloRandomSampleGenerator();

        vuelo.addColombofiloVuelo(colombofiloVueloBack);
        assertThat(vuelo.getColombofiloVuelos()).containsOnly(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getVuelo()).isEqualTo(vuelo);

        vuelo.removeColombofiloVuelo(colombofiloVueloBack);
        assertThat(vuelo.getColombofiloVuelos()).doesNotContain(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getVuelo()).isNull();

        vuelo.colombofiloVuelos(new HashSet<>(Set.of(colombofiloVueloBack)));
        assertThat(vuelo.getColombofiloVuelos()).containsOnly(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getVuelo()).isEqualTo(vuelo);

        vuelo.setColombofiloVuelos(new HashSet<>());
        assertThat(vuelo.getColombofiloVuelos()).doesNotContain(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getVuelo()).isNull();
    }

    //@Test
    void premioTest() throws Exception {
        Vuelo vuelo = getVueloRandomSampleGenerator();
        Premio premioBack = getPremioRandomSampleGenerator();

        vuelo.addPremio(premioBack);
        assertThat(vuelo.getPremios()).containsOnly(premioBack);
        assertThat(premioBack.getVuelo()).isEqualTo(vuelo);

        vuelo.removePremio(premioBack);
        assertThat(vuelo.getPremios()).doesNotContain(premioBack);
        assertThat(premioBack.getVuelo()).isNull();

        vuelo.premios(new HashSet<>(Set.of(premioBack)));
        assertThat(vuelo.getPremios()).containsOnly(premioBack);
        assertThat(premioBack.getVuelo()).isEqualTo(vuelo);

        vuelo.setPremios(new HashSet<>());
        assertThat(vuelo.getPremios()).doesNotContain(premioBack);
        assertThat(premioBack.getVuelo()).isNull();
    }

    //@Test
    void paraderoTest() throws Exception {
        Vuelo vuelo = getVueloRandomSampleGenerator();
        Paradero paraderoBack = getParaderoRandomSampleGenerator();

        vuelo.setParadero(paraderoBack);
        assertThat(vuelo.getParadero()).isEqualTo(paraderoBack);

        vuelo.paradero(null);
        assertThat(vuelo.getParadero()).isNull();
    }
}
