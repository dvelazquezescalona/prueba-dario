package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ColorTestSamples.*;
import static cu.fcc.pigeon.domain.PalomaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ColorTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Color.class);
        Color color1 = getColorSample1();
        Color color2 = new Color();
        assertThat(color1).isNotEqualTo(color2);

        color2.setId(color1.getId());
        assertThat(color1).isEqualTo(color2);

        color2 = getColorSample2();
        assertThat(color1).isNotEqualTo(color2);
    }

    //@Test
    void palomaTest() throws Exception {
        Color color = getColorRandomSampleGenerator();
        Paloma palomaBack = getPalomaRandomSampleGenerator();

        color.addPaloma(palomaBack);
        assertThat(color.getPalomas()).containsOnly(palomaBack);
        assertThat(palomaBack.getColor()).isEqualTo(color);

        color.removePaloma(palomaBack);
        assertThat(color.getPalomas()).doesNotContain(palomaBack);
        assertThat(palomaBack.getColor()).isNull();

        color.palomas(new HashSet<>(Set.of(palomaBack)));
        assertThat(color.getPalomas()).containsOnly(palomaBack);
        assertThat(palomaBack.getColor()).isEqualTo(color);

        color.setPalomas(new HashSet<>());
        assertThat(color.getPalomas()).doesNotContain(palomaBack);
        assertThat(palomaBack.getColor()).isNull();
    }
}
