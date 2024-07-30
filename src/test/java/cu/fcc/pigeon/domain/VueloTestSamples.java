package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VueloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vuelo getVueloSample1() {
        return new Vuelo().id(1L).descripcion("descripcion1").competencia("competencia1").campeonato("campeonato1");
    }

    public static Vuelo getVueloSample2() {
        return new Vuelo().id(2L).descripcion("descripcion2").competencia("competencia2").campeonato("campeonato2");
    }

    public static Vuelo getVueloRandomSampleGenerator() {
        return new Vuelo()
            .id(longCount.incrementAndGet())
            .descripcion(UUID.randomUUID().toString())
            .competencia(UUID.randomUUID().toString())
            .campeonato(UUID.randomUUID().toString());
    }
}
