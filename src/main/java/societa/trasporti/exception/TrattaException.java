package societa.trasporti.exception;

public class TrattaException extends RuntimeException {
    public TrattaException() {
        super("Impossibile assegnare la tratta fornita dato che è attualmente percorsa da un altro veicolo");
    }
}