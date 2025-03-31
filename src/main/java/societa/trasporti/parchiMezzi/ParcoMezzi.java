package societa.trasporti.parchiMezzi;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mezzi")
public abstract class ParcoMezzi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long matricola;

    @Enumerated(EnumType.STRING)
    private TipoVeicolo tipoVeicolo;

    @Column
    private int capienza;

    @Column
    private boolean inServizio;

    @ManyToOne // Relazione Many-to-One con Tratta
    @JoinColumn(name = "id_tratta") // Chiave esterna verso Tratta
    private Tratta tratta;
}