package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ColombofiloVueloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ColombofiloVuelo getColombofiloVueloSample1() {
        return new ColombofiloVuelo().id(1L).enviadas(1);
    }

    public static ColombofiloVuelo getColombofiloVueloSample2() {
        return new ColombofiloVuelo().id(2L).enviadas(2);
    }

    public static ColombofiloVuelo getColombofiloVueloRandomSampleGenerator() {
        return new ColombofiloVuelo().id(longCount.incrementAndGet()).enviadas(intCount.incrementAndGet());
    }
}
