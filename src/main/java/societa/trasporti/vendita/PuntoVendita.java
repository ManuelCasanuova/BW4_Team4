package societa.trasporti.vendita;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.titoloViaggio.TitoloViaggio;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="punti_vendita")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoVendita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="punto_vendita_id")
    private Long id;

    @Column(nullable = false)
    private String indirizzo;

    @OneToMany(mappedBy = "puntoVendita", cascade = CascadeType.ALL)
    private List<TitoloViaggio> titoliViaggio;

    public PuntoVendita(String indirizzo) {
        this.indirizzo = indirizzo;

    }
}
