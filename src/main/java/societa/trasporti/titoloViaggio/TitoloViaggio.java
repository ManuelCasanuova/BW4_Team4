package societa.trasporti.titoloViaggio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.vendita.PuntoVendita;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "titoli_viaggio")

public abstract class TitoloViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "codice_univoco")
    private Long codiceUnivoco;

    @Column(name = "prezzo_titolo_viaggio", nullable = false)
    private double prezzoViaggio;

    @Column(name = "data_aquisto", nullable = false)
    private LocalDate dataAquisto;

    @ManyToOne
    @JoinColumn(name = "id_punto_vendita", nullable = false)
    private PuntoVendita puntoVendita;
}