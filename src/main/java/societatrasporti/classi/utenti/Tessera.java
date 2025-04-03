package societatrasporti.classi.utenti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societatrasporti.classi.titoloViaggio.abbonamento.Abbonamento;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "tessere")
@NoArgsConstructor
public class Tessera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tessera_id")
    private Long id;

    @Column(name = "data_di_emissione", nullable = false)
    private LocalDate dataDiEmissione;

    @Column(name = "data_di_scadenza", nullable = false)
    private LocalDate dataDiScadenza;

    @OneToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Abbonamento> abbonamentiList;

    public Tessera(Utente utente, LocalDate dataDiEmissione) {
        this.utente = utente;
        this.dataDiEmissione = dataDiEmissione;
        this.dataDiScadenza = dataDiEmissione.plusYears(1);
    }
}
