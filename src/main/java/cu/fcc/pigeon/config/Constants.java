package cu.fcc.pigeon.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final int CONST_VELOCIDAD = 60000;
    public static final double RADIO_TIERRA_KM = 6371000.8;

    public static final int KM_A_METROS = 1000;

    private Constants() {}
}
