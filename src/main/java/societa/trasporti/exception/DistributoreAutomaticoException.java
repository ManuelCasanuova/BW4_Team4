package societa.trasporti.exception;

// Eccezione generica per i distributori automatici
public class DistributoreAutomaticoException extends RuntimeException {

    public DistributoreAutomaticoException(Long id) {
        super("Distributore automatico con id " + id + " non trovato");
    }

}

