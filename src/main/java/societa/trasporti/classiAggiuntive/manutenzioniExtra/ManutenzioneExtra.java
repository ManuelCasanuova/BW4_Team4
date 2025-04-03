package societa.trasporti.classiAggiuntive.manutenzioniExtra;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.parchiMezzi.ParcoMezzi;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "manutenzioni_extra")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ManutenzioneExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_manutenzione")
    private TecnicoManutenzioneExtra tecnicoManutenzioneExtra;

    @Column(name = "data_inizio_manutenzione")
    private LocalDate dataInizioManutenzione;

    @Column(name = "data_fine_manutenzione")
    private LocalDate dataFineManutenzione;

    @Column(name = "costo_manutenzione")
    private double costoManutenzione;

    // inserire relazione in ParcoMezzi
    @ManyToMany
    @JoinColumn(name = "id_mezzo")
    private ParcoMezzi parcoMezzi;

    public ManutenzioneExtra(TecnicoManutenzioneExtra tecnicoManutenzioneExtra, LocalDate dataInizioManutenzione, LocalDate dataFineManutenzione, double costoManutenzione, ParcoMezzi parcoMezzi) {
        this.tecnicoManutenzioneExtra = tecnicoManutenzioneExtra;
        this.dataInizioManutenzione = dataInizioManutenzione;
        this.dataFineManutenzione = dataFineManutenzione;
        this.costoManutenzione = costoManutenzione;
        this.parcoMezzi = parcoMezzi;
    }


}
