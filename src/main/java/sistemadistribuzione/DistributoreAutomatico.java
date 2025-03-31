package sistemadistribuzione;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "distributori_automatici")
public class DistributoreAutomatico extends SistemaEmettitore {

    private StatoDistributore stato;

    public DistributoreAutomatico(Long id, StatoDistributore stato) {
        super(id);
        this.stato = stato;
    }
}