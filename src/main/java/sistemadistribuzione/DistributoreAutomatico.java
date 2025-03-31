package sistemadistribuzione;

public class DistributoreAutomatico extends SistemaEmettitore{

    private StatoDistributore stato;


    public DistributoreAutomatico(Long id, StatoDistributore stato) {
        super(id);
        this.stato = stato;
    }

    public StatoDistributore getStato() {
        return stato;
    }

    public void setStato(StatoDistributore stato) {
        this.stato = stato;
    }
}
