package titoloViaggio;

import jakarta.persistence.*;
import lombok.Data;
import sistemadistribuzione.SistemaEmettitore;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "titoli_viaggio")
public abstract class TitoloViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codiceUnivoco;

    @Column
    private LocalDate dataEmissione;

    @ManyToOne // Relazione Many-to-One con SistemaEmettitore
    @JoinColumn(name = "id_sistema_emettitore") // Chiave esterna verso SistemaEmettitore
    private SistemaEmettitore sistemaEmettitore;
}