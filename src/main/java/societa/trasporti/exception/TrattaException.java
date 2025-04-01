package societa.trasporti.exception;

public class TrattaException extends RuntimeException {
    public TrattaException(String message) {
        super(message);
    }

    public TrattaException(String message, Throwable cause) {
        super(message, cause);
    }
}