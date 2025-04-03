package societatrasporti.classi.vendita.distributori;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societatrasporti.classi.vendita.PuntoVendita;

@Entity
@Data
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
