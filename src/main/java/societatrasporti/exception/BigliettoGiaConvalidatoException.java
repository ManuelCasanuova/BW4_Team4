package societatrasporti.exception;

public class BigliettoGiaConvalidatoException extends RuntimeException {
    public BigliettoGiaConvalidatoException() {
        super("Il biglietto non è utilizzabile dato che è gia stato convalidato");
    }
}
