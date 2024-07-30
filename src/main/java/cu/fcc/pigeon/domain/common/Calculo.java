package cu.fcc.pigeon.domain.common;

import cu.fcc.pigeon.config.Constants;

public class Calculo {

    public static double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
        // Convertir las latitudes y longitudes de grados a radianes
        double latitud1Rad = Math.toRadians(latitud1);
        double longitud1Rad = Math.toRadians(longitud1);
        double latitud2Rad = Math.toRadians(latitud2);
        double longitud2Rad = Math.toRadians(longitud2);

        // Calcular la diferencia de latitud y longitud
        double difLatitud = latitud2Rad - latitud1Rad;
        double difLongitud = longitud2Rad - longitud1Rad;

        // Calcular la distancia usando la fórmula de Haversine
        double a =
            Math.pow(Math.sin(difLatitud / 2), 2) + Math.cos(latitud1Rad) * Math.cos(latitud2Rad) * Math.pow(Math.sin(difLongitud / 2), 2);
        double distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Multiplicar la distancia por el radio de la Tierra para obtener la distancia en kilómetros
        return (Constants.RADIO_TIERRA_KM * distancia) / 1000;
    }

    private Calculo() {}
}
