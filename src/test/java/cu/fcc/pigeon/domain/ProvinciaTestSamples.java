package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProvinciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Provincia getProvinciaSample1() {
        return new Provincia().id(1L).nombreProvincia("nombreProvincia1");
    }

    public static Provincia getProvinciaSample2() {
        return new Provincia().id(2L).nombreProvincia("nombreProvincia2");
    }

    public static Provincia getProvinciaRandomSampleGenerator() {
        return new Provincia().id(longCount.incrementAndGet()).nombreProvincia(UUID.randomUUID().toString());
    }
}
