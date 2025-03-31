package titoloViaggio.biglietto;

import jakarta.persistence.*;
import lombok.Data;
import titoloViaggio.TitoloViaggio;
import utenti.Utente;

@Data
@Entity
@Table(name = "biglietti")
public class Biglietto extends TitoloViaggio {

    @Column
    private boolean ridotto;

    @ManyToOne // Relazione Many-to-One con Utente
    @JoinColumn(name = "id_utente") // Chiave esterna verso Utente
    private Utente utente;
}