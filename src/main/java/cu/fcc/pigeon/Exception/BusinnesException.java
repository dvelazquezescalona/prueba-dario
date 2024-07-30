package cu.fcc.pigeon.Exception;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

public class BusinnesException extends Exception {

    public BusinnesException() {}

    public BusinnesException(String message) {
        super(message);
    }

    public BusinnesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinnesException(Throwable cause) {
        super(cause);
    }

    public BusinnesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
