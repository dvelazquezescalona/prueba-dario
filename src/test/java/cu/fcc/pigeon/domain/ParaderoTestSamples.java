package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ParaderoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Paradero getParaderoSample1() {
        return new Paradero().id(1L).nombreParadero("nombreParadero1").distanciaMedia(1);
    }

    public static Paradero getParaderoSample2() {
        return new Paradero().id(2L).nombreParadero("nombreParadero2").distanciaMedia(2);
    }

    public static Paradero getParaderoRandomSampleGenerator() {
        return new Paradero()
            .id(longCount.incrementAndGet())
            .nombreParadero(UUID.randomUUID().toString())
            .distanciaMedia(intCount.incrementAndGet());
    }
}
