package societatrasporti.classi.titoloViaggio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societatrasporti.classi.vendita.PuntoVendita;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "getAllTitoliViaggio", query = "SELECT t FROM TitoloViaggio t")
@NoArgsConstructor
@Table(name = "titoli_viaggio")
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice_univoco")
    private Long codiceUnivoco;

    @Column(name = "prezzo_titolo_viaggio", nullable = false)
    private double prezzoViaggio;

    @Column(name = "data_acquisto", nullable = false)
    private LocalDate dataAcquisto;

    @ManyToOne
    @JoinColumn(name = "punto_vendita_id")
    private PuntoVendita puntoVendita;

    public TitoloViaggio(Long codiceUnivoco, double prezzoViaggio, LocalDate dataAcquisto, PuntoVendita puntoVendita) {
        this.codiceUnivoco = codiceUnivoco;
        this.prezzoViaggio = prezzoViaggio;
        this.dataAcquisto = dataAcquisto;
        this.puntoVendita = puntoVendita;
    }
}
