package sistemadistribuzione;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="distributori_automatici")

public class Distributiore extends PuntoVendita {

    @Column(nullable = false)
    private boolean attivo;



}
