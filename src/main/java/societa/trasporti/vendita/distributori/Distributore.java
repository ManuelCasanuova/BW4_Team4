package societa.trasporti.vendita.distributori;

import jakarta.persistence.*;
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
