package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ColombofiloTestSamples.*;
import static cu.fcc.pigeon.domain.ColorTestSamples.*;
import static cu.fcc.pigeon.domain.PalomaTestSamples.*;
import static cu.fcc.pigeon.domain.PremioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PalomaTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paloma.class);
        Paloma paloma1 = getPalomaSample1();
        Paloma paloma2 = new Paloma();
        assertThat(paloma1).isNotEqualTo(paloma2);

        paloma2.setId(paloma1.getId());
        assertThat(paloma1).isEqualTo(paloma2);

        paloma2 = getPalomaSample2();
        assertThat(paloma1).isNotEqualTo(paloma2);
    }

    //@Test
    void premioTest() throws Exception {
        Paloma paloma = getPalomaRandomSampleGenerator();
        Premio premioBack = getPremioRandomSampleGenerator();

        paloma.addPremio(premioBack);
        assertThat(paloma.getPremios()).containsOnly(premioBack);
        assertThat(premioBack.getPaloma()).isEqualTo(paloma);

        paloma.removePremio(premioBack);
        assertThat(paloma.getPremios()).doesNotContain(premioBack);
        assertThat(premioBack.getPaloma()).isNull();

        paloma.premios(new HashSet<>(Set.of(premioBack)));
        assertThat(paloma.getPremios()).containsOnly(premioBack);
        assertThat(premioBack.getPaloma()).isEqualTo(paloma);

        paloma.setPremios(new HashSet<>());
        assertThat(paloma.getPremios()).doesNotContain(premioBack);
        assertThat(premioBack.getPaloma()).isNull();
    }

    //@Test
    void colorTest() throws Exception {
        Paloma paloma = getPalomaRandomSampleGenerator();
        Color colorBack = getColorRandomSampleGenerator();

        paloma.setColor(colorBack);
        assertThat(paloma.getColor()).isEqualTo(colorBack);

        paloma.color(null);
        assertThat(paloma.getColor()).isNull();
    }

    //@Test
    void colombofiloTest() throws Exception {
        Paloma paloma = getPalomaRandomSampleGenerator();
        Colombofilo colombofiloBack = getColombofiloRandomSampleGenerator();

        paloma.setColombofilo(colombofiloBack);
        assertThat(paloma.getColombofilo()).isEqualTo(colombofiloBack);

        paloma.colombofilo(null);
        assertThat(paloma.getColombofilo()).isNull();
    }
}
