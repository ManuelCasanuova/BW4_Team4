package societa.trasporti.exception;

public class UtenteException extends RuntimeException {
    // eccezione per utente non trovato
    public UtenteException(Long id) {
        super("Utente con id " + id + " non trovato");
    }
}
