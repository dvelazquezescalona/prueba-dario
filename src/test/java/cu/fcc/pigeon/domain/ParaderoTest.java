package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ParaderoTestSamples.*;
import static cu.fcc.pigeon.domain.SociedadTestSamples.*;
import static cu.fcc.pigeon.domain.VueloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ParaderoTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paradero.class);
        Paradero paradero1 = getParaderoSample1();
        Paradero paradero2 = new Paradero();
        assertThat(paradero1).isNotEqualTo(paradero2);

        paradero2.setId(paradero1.getId());
        assertThat(paradero1).isEqualTo(paradero2);

        paradero2 = getParaderoSample2();
        assertThat(paradero1).isNotEqualTo(paradero2);
    }

    //@Test
    void vueloTest() throws Exception {
        Paradero paradero = getParaderoRandomSampleGenerator();
        Vuelo vueloBack = getVueloRandomSampleGenerator();

        paradero.addVuelo(vueloBack);
        assertThat(paradero.getVuelos()).containsOnly(vueloBack);
        assertThat(vueloBack.getParadero()).isEqualTo(paradero);

        paradero.removeVuelo(vueloBack);
        assertThat(paradero.getVuelos()).doesNotContain(vueloBack);
        assertThat(vueloBack.getParadero()).isNull();

        paradero.vuelos(new HashSet<>(Set.of(vueloBack)));
        assertThat(paradero.getVuelos()).containsOnly(vueloBack);
        assertThat(vueloBack.getParadero()).isEqualTo(paradero);

        paradero.setVuelos(new HashSet<>());
        assertThat(paradero.getVuelos()).doesNotContain(vueloBack);
        assertThat(vueloBack.getParadero()).isNull();
    }

    //@Test
    void sociedadTest() throws Exception {
        Paradero paradero = getParaderoRandomSampleGenerator();
        Sociedad sociedadBack = getSociedadRandomSampleGenerator();

        paradero.setSociedad(sociedadBack);
        assertThat(paradero.getSociedad()).isEqualTo(sociedadBack);

        paradero.sociedad(null);
        assertThat(paradero.getSociedad()).isNull();
    }
}
