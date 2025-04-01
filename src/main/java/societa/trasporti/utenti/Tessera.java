package societa.trasporti.utenti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.titoloViaggio.abbonamento.Abbonamento;

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

    @Column(name = "data_di_emissione")
    private LocalDate dataDiEmissione;

    @Column(name = "data_di_scadenza")
    private LocalDate dataDiScadenza;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private List<Abbonamento> abbonamentiList;

    public Tessera(Utente utente, LocalDate dataDiEmissione) {
        this.utente = utente;
        this.dataDiEmissione = dataDiEmissione;
        this.dataDiScadenza = dataDiEmissione.plusYears(1);
    }
}