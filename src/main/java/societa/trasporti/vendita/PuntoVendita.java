package societa.trasporti.vendita;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.titoloViaggio.TitoloViaggio;

import java.util.List;

@Data
@NoArgsConstructor
public abstract class PuntoVendita {

    @Id
    @GeneratedValue
    @Column(name="punto_vendita_id")
    private Long id;

    @Column(nullable = false)
    private String indirizzo;

    @OneToOne(mappedBy = "puntoVendita")
    private List<TitoloViaggio> titoliViaggio;

    public PuntoVendita(String indirizzo) {
        this.indirizzo = indirizzo;

    }
}
