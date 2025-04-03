package societa.trasporti.classiAggiuntive.turno;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.tratta.Tratta;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "turni")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_turno")
    private TipoTurno tipoTurno;

    @Column(name = "ora_inizio")
    private LocalTime oraInizio;

    @Column(name = "ora_fine")
    private LocalTime oraFine;

    // Inserire relazione su Tratta
    @OneToOne
    @JoinColumn(name = "id_tratta")
    public Tratta tratta;

    // Inserire relazione su ParcoMezzi
    @OneToOne
    @JoinColumn(name = "id_mezzo")
    public ParcoMezzi mezzo;

    public Turno(TipoTurno tipoTurno, LocalTime oraInizio, LocalTime oraFine, Tratta tratta, ParcoMezzi mezzo) {
        this.tipoTurno = tipoTurno;
        this.oraInizio = oraInizio;
        this.oraFine = oraInizio.plusHours(6);
        this.tratta = tratta;
        this.mezzo = mezzo;
    }


}
