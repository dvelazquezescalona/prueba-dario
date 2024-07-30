package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.MunicipioTestSamples.*;
import static cu.fcc.pigeon.domain.ProvinciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProvinciaTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provincia.class);
        Provincia provincia1 = getProvinciaSample1();
        Provincia provincia2 = new Provincia();
        assertThat(provincia1).isNotEqualTo(provincia2);

        provincia2.setId(provincia1.getId());
        assertThat(provincia1).isEqualTo(provincia2);

        provincia2 = getProvinciaSample2();
        assertThat(provincia1).isNotEqualTo(provincia2);
    }

    //@Test
    void municipioTest() throws Exception {
        Provincia provincia = getProvinciaRandomSampleGenerator();
        Municipio municipioBack = getMunicipioRandomSampleGenerator();

        provincia.addMunicipio(municipioBack);
        assertThat(provincia.getMunicipios()).containsOnly(municipioBack);
        assertThat(municipioBack.getProvincia()).isEqualTo(provincia);

        provincia.removeMunicipio(municipioBack);
        assertThat(provincia.getMunicipios()).doesNotContain(municipioBack);
        assertThat(municipioBack.getProvincia()).isNull();

        provincia.municipios(new HashSet<>(Set.of(municipioBack)));
        assertThat(provincia.getMunicipios()).containsOnly(municipioBack);
        assertThat(municipioBack.getProvincia()).isEqualTo(provincia);

        provincia.setMunicipios(new HashSet<>());
        assertThat(provincia.getMunicipios()).doesNotContain(municipioBack);
        assertThat(municipioBack.getProvincia()).isNull();
    }
}
