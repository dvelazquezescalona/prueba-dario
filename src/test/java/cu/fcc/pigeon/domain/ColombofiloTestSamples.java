package cu.fcc.pigeon.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ColombofiloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Colombofilo getColombofiloSample1() {
        return new Colombofilo()
            .id(1L)
            .nombre("nombre1")
            .primerApellido("primerApellido1")
            .segindoApellido("segindoApellido1")
            .ci("ci1")
            .direccion("direccion1")
            .categoria("categoria1")
            .telefono("telefono1")
            .zona("zona1");
    }

    public static Colombofilo getColombofiloSample2() {
        return new Colombofilo()
            .id(2L)
            .nombre("nombre2")
            .primerApellido("primerApellido2")
            .segindoApellido("segindoApellido2")
            .ci("ci2")
            .direccion("direccion2")
            .categoria("categoria2")
            .telefono("telefono2")
            .zona("zona2");
    }

    public static Colombofilo getColombofiloRandomSampleGenerator() {
        return new Colombofilo()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .primerApellido(UUID.randomUUID().toString())
            .segindoApellido(UUID.randomUUID().toString())
            .ci(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .categoria(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .zona(UUID.randomUUID().toString());
    }
}
