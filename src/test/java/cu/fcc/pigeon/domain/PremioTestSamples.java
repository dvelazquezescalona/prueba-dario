package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PremioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Premio getPremioSample1() {
        return new Premio().id(1L).tiempoVuelo(1D).lugar(1);
    }

    public static Premio getPremioSample2() {
        return new Premio().id(2L).lugar(2);
    }

    public static Premio getPremioRandomSampleGenerator() {
        return new Premio().id(longCount.incrementAndGet()).lugar(intCount.incrementAndGet());
    }
}
