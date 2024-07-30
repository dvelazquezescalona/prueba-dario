package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PalomaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Paloma getPalomaSample1() {
        return new Paloma().id(1L).anilla("anilla1").anno("anno1");
    }

    public static Paloma getPalomaSample2() {
        return new Paloma().id(2L).anilla("anilla2").anno("anno2");
    }

    public static Paloma getPalomaRandomSampleGenerator() {
        return new Paloma().id(longCount.incrementAndGet()).anilla(UUID.randomUUID().toString()).anno(UUID.randomUUID().toString());
    }
}
