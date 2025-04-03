package societa.trasporti.classiAggiuntive.patente;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.classiAggiuntive.Autista;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "patenti")
@NoArgsConstructor
public class Patente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long numeroPatente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_patente")
    private TipoPatente tipoPatente;

    @OneToOne
    @JoinColumn(name = "autista")
    private Autista autista;

    @Column(name = "data_rilascio", nullable = false)
    private LocalDate dataRilascio;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    public Patente(TipoPatente tipoPatente, Autista autista, LocalDate dataRilascio, LocalDate dataScadenza) {
        this.tipoPatente = tipoPatente;
        this.autista = autista;
        this.dataRilascio = dataRilascio;
        this.dataScadenza = dataScadenza;
    }

}
