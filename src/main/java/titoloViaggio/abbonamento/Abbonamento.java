package titoloViaggio.abbonamento;

import jakarta.persistence.*;
import lombok.Data;
import utenti.Utente;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "abbonamenti")
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

    @Column
    private LocalDate dataInizio;

    @Column
    private LocalDate dataFine;

    @ManyToOne // Relazione Many-to-One con Utente
    @JoinColumn(name = "id_utente") // Chiave esterna verso Utente
    private Utente utente;
}