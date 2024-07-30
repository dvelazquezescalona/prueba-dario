package cu.fcc.pigeon.domain;

import static cu.fcc.pigeon.domain.ColombofiloTestSamples.*;
import static cu.fcc.pigeon.domain.ColombofiloVueloTestSamples.*;
import static cu.fcc.pigeon.domain.PalomaTestSamples.*;
import static cu.fcc.pigeon.domain.SociedadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cu.fcc.pigeon.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ColombofiloTest {

    //@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colombofilo.class);
        Colombofilo colombofilo1 = getColombofiloSample1();
        Colombofilo colombofilo2 = new Colombofilo();
        assertThat(colombofilo1).isNotEqualTo(colombofilo2);

        colombofilo2.setId(colombofilo1.getId());
        assertThat(colombofilo1).isEqualTo(colombofilo2);

        colombofilo2 = getColombofiloSample2();
        assertThat(colombofilo1).isNotEqualTo(colombofilo2);
    }

    //@Test
    void palomaTest() throws Exception {
        Colombofilo colombofilo = getColombofiloRandomSampleGenerator();
        Paloma palomaBack = getPalomaRandomSampleGenerator();

        colombofilo.addPaloma(palomaBack);
        assertThat(colombofilo.getPalomas()).containsOnly(palomaBack);
        assertThat(palomaBack.getColombofilo()).isEqualTo(colombofilo);

        colombofilo.removePaloma(palomaBack);
        assertThat(colombofilo.getPalomas()).doesNotContain(palomaBack);
        assertThat(palomaBack.getColombofilo()).isNull();

        colombofilo.palomas(new HashSet<>(Set.of(palomaBack)));
        assertThat(colombofilo.getPalomas()).containsOnly(palomaBack);
        assertThat(palomaBack.getColombofilo()).isEqualTo(colombofilo);

        colombofilo.setPalomas(new HashSet<>());
        assertThat(colombofilo.getPalomas()).doesNotContain(palomaBack);
        assertThat(palomaBack.getColombofilo()).isNull();
    }

    //@Test
    void colombofiloVueloTest() throws Exception {
        Colombofilo colombofilo = getColombofiloRandomSampleGenerator();
        ColombofiloVuelo colombofiloVueloBack = getColombofiloVueloRandomSampleGenerator();

        colombofilo.addColombofiloVuelo(colombofiloVueloBack);
        assertThat(colombofilo.getColombofiloVuelos()).containsOnly(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getColombofilo()).isEqualTo(colombofilo);

        colombofilo.removeColombofiloVuelo(colombofiloVueloBack);
        assertThat(colombofilo.getColombofiloVuelos()).doesNotContain(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getColombofilo()).isNull();

        colombofilo.colombofiloVuelos(new HashSet<>(Set.of(colombofiloVueloBack)));
        assertThat(colombofilo.getColombofiloVuelos()).containsOnly(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getColombofilo()).isEqualTo(colombofilo);

        colombofilo.setColombofiloVuelos(new HashSet<>());
        assertThat(colombofilo.getColombofiloVuelos()).doesNotContain(colombofiloVueloBack);
        assertThat(colombofiloVueloBack.getColombofilo()).isNull();
    }

    //@Test
    void sociedadTest() throws Exception {
        Colombofilo colombofilo = getColombofiloRandomSampleGenerator();
        Sociedad sociedadBack = getSociedadRandomSampleGenerator();

        colombofilo.setSociedad(sociedadBack);
        assertThat(colombofilo.getSociedad()).isEqualTo(sociedadBack);

        colombofilo.sociedad(null);
        assertThat(colombofilo.getSociedad()).isNull();
    }
}
