package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SociedadTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sociedad getSociedadSample1() {
        return new Sociedad().id(1L).nombreSociedad("nombreSociedad1");
    }

    public static Sociedad getSociedadSample2() {
        return new Sociedad().id(2L).nombreSociedad("nombreSociedad2");
    }

    public static Sociedad getSociedadRandomSampleGenerator() {
        return new Sociedad().id(longCount.incrementAndGet()).nombreSociedad(UUID.randomUUID().toString());
    }
}
