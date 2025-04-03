package societatrasporti.exception;

public class TrattaPercorsaException extends RuntimeException {
    public TrattaPercorsaException() {
        super("Impossibile assegnare la tratta fornita dato che è attualmente percorsa da un altro veicolo");
    }
}