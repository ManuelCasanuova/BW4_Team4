package societa.trasporti.servizi;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.tratta.Tratta;

import java.time.LocalDate;

@Entity
@Table(name = "servizi")
@NoArgsConstructor
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "servizio_id")
    private Long id;

    @Column(name = "data_inizio")
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "parco_mezzi_id")
    private ParcoMezzi parcoMezzi;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;


    public Servizio(LocalDate dataInizio, LocalDate dataFine, ParcoMezzi parcoMezzi, Tratta tratta) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.parcoMezzi = parcoMezzi;
        this.tratta = tratta;
    }


    public Servizio(ParcoMezzi parcoMezzi, Tratta tratta){
        this.dataInizio = LocalDate.now();
        this.parcoMezzi = parcoMezzi;
        this.tratta = tratta;
    }
}
