package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ColombofiloTestSamples.*;
import static cu.fcc.pigeon.domain.MunicipioTestSamples.*;
import static cu.fcc.pigeon.domain.ParaderoTestSamples.*;
import static cu.fcc.pigeon.domain.SociedadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SociedadTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sociedad.class);
        Sociedad sociedad1 = getSociedadSample1();
        Sociedad sociedad2 = new Sociedad();
        assertThat(sociedad1).isNotEqualTo(sociedad2);

        sociedad2.setId(sociedad1.getId());
        assertThat(sociedad1).isEqualTo(sociedad2);

        sociedad2 = getSociedadSample2();
        assertThat(sociedad1).isNotEqualTo(sociedad2);
    }

    //@Test
    void colombofiloTest() throws Exception {
        Sociedad sociedad = getSociedadRandomSampleGenerator();
        Colombofilo colombofiloBack = getColombofiloRandomSampleGenerator();

        sociedad.addColombofilo(colombofiloBack);
        assertThat(sociedad.getColombofilos()).containsOnly(colombofiloBack);
        assertThat(colombofiloBack.getSociedad()).isEqualTo(sociedad);

        sociedad.removeColombofilo(colombofiloBack);
        assertThat(sociedad.getColombofilos()).doesNotContain(colombofiloBack);
        assertThat(colombofiloBack.getSociedad()).isNull();

        sociedad.colombofilos(new HashSet<>(Set.of(colombofiloBack)));
        assertThat(sociedad.getColombofilos()).containsOnly(colombofiloBack);
        assertThat(colombofiloBack.getSociedad()).isEqualTo(sociedad);

        sociedad.setColombofilos(new HashSet<>());
        assertThat(sociedad.getColombofilos()).doesNotContain(colombofiloBack);
        assertThat(colombofiloBack.getSociedad()).isNull();
    }

    //@Test
    void paraderoTest() throws Exception {
        Sociedad sociedad = getSociedadRandomSampleGenerator();
        Paradero paraderoBack = getParaderoRandomSampleGenerator();

        sociedad.addParadero(paraderoBack);
        assertThat(sociedad.getParaderos()).containsOnly(paraderoBack);
        assertThat(paraderoBack.getSociedad()).isEqualTo(sociedad);

        sociedad.removeParadero(paraderoBack);
        assertThat(sociedad.getParaderos()).doesNotContain(paraderoBack);
        assertThat(paraderoBack.getSociedad()).isNull();

        sociedad.paraderos(new HashSet<>(Set.of(paraderoBack)));
        assertThat(sociedad.getParaderos()).containsOnly(paraderoBack);
        assertThat(paraderoBack.getSociedad()).isEqualTo(sociedad);

        sociedad.setParaderos(new HashSet<>());
        assertThat(sociedad.getParaderos()).doesNotContain(paraderoBack);
        assertThat(paraderoBack.getSociedad()).isNull();
    }

    //@Test
    void municipioTest() throws Exception {
        Sociedad sociedad = getSociedadRandomSampleGenerator();
        Municipio municipioBack = getMunicipioRandomSampleGenerator();

        sociedad.setMunicipio(municipioBack);
        assertThat(sociedad.getMunicipio()).isEqualTo(municipioBack);

        sociedad.municipio(null);
        assertThat(sociedad.getMunicipio()).isNull();
    }
}
