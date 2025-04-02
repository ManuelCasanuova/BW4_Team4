package societa.trasporti.titoloViaggio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.vendita.PuntoVendita;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_titolo", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
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
}
