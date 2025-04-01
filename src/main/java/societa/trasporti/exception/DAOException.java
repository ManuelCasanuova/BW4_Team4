package societa.trasporti.exception;

//Viene lanciata quando si verifica un errore generale nell'accesso ai dati
public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
