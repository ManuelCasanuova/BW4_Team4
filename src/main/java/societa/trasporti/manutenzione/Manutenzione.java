package societa.trasporti.manutenzione;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.parchiMezzi.ParcoMezzi;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "manutenzioni")
@NoArgsConstructor

public class Manutenzione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "manutenzione_id")
    private Long id;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    private LocalDate dataFine;

    @Column(name = "tipo_manutenzione")
    @Enumerated(EnumType.STRING)
    private TipoManutenzione tipoManutenzione;

    @ManyToOne
    @JoinColumn(name = "parco_mezzi_id")
    private ParcoMezzi parcoMezzi;


    public Manutenzione(LocalDate dataInizio, LocalDate dataFine, TipoManutenzione tipoManutenzione, ParcoMezzi parcoMezzi) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tipoManutenzione = tipoManutenzione;
        this.parcoMezzi = parcoMezzi;
    }

    public Manutenzione(TipoManutenzione tipoManutenzione, ParcoMezzi parcoMezzi){
        this.dataInizio = LocalDate.now();
        this.tipoManutenzione = tipoManutenzione;
        this.parcoMezzi = parcoMezzi;
    }


}
