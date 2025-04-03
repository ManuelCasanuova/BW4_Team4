package societatrasporti.exception;

public class ServizioNotFoundException extends RuntimeException {
    public ServizioNotFoundException(Long id) {
        super("Servizio con ID " + id + " non trovato.");
    }
}
