package societatrasporti.exception;

public class DistributoreFuoriServizioException extends RuntimeException {
    public DistributoreFuoriServizioException(){
        super("Il distributore è fuori servizio");
    }
}
