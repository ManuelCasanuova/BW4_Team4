package societa.trasporti.vendita.distributori;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.vendita.PuntoVendita;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="distributori_automatici")

public class Distributore extends PuntoVendita {

    @Column(nullable = false)
    private boolean attivo;

    public Distributore(String indirizzo, boolean attivo) {
        super(indirizzo);
        this.attivo = attivo;
    }

}
