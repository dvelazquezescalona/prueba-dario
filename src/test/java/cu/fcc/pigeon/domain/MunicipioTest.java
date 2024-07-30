package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.MunicipioTestSamples.*;
import static cu.fcc.pigeon.domain.ProvinciaTestSamples.*;
import static cu.fcc.pigeon.domain.SociedadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MunicipioTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipio.class);
        Municipio municipio1 = getMunicipioSample1();
        Municipio municipio2 = new Municipio();
        assertThat(municipio1).isNotEqualTo(municipio2);

        municipio2.setId(municipio1.getId());
        assertThat(municipio1).isEqualTo(municipio2);

        municipio2 = getMunicipioSample2();
        assertThat(municipio1).isNotEqualTo(municipio2);
    }

    //@Test
    void sociedadTest() throws Exception {
        Municipio municipio = getMunicipioRandomSampleGenerator();
        Sociedad sociedadBack = getSociedadRandomSampleGenerator();

        municipio.addSociedad(sociedadBack);
        assertThat(municipio.getSociedades()).containsOnly(sociedadBack);
        assertThat(sociedadBack.getMunicipio()).isEqualTo(municipio);

        municipio.removeSociedad(sociedadBack);
        assertThat(municipio.getSociedades()).doesNotContain(sociedadBack);
        assertThat(sociedadBack.getMunicipio()).isNull();

        municipio.sociedades(new HashSet<>(Set.of(sociedadBack)));
        assertThat(municipio.getSociedades()).containsOnly(sociedadBack);
        assertThat(sociedadBack.getMunicipio()).isEqualTo(municipio);

        municipio.setSociedades(new HashSet<>());
        assertThat(municipio.getSociedades()).doesNotContain(sociedadBack);
        assertThat(sociedadBack.getMunicipio()).isNull();
    }

    //@Test
    void provinciaTest() throws Exception {
        Municipio municipio = getMunicipioRandomSampleGenerator();
        Provincia provinciaBack = getProvinciaRandomSampleGenerator();

        municipio.setProvincia(provinciaBack);
        assertThat(municipio.getProvincia()).isEqualTo(provinciaBack);

        municipio.provincia(null);
        assertThat(municipio.getProvincia()).isNull();
    }
}
