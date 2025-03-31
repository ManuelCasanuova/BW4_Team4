package utenti;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tessere")
public class Tessera {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column( unique = true)
    private String numeroTessera;

    @Column
    private LocalDate dataDiEmissione;

    @Column
    private LocalDate dataDiScadenza;

    @OneToOne // Relazione uno-a-uno con Utente
    @JoinColumn(name = "id_utente", nullable = false) // Chiave esterna verso Utente
    private Utente utente;

    public boolean isValid() {
        return LocalDate.now().isBefore(dataDiScadenza);
    }
}