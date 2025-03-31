package titoloViaggio.biglietto;

import jakarta.persistence.Entity;
import titoloViaggio.TitoloViaggio;

@Entity
public class Biglietto extends TitoloViaggio {
    private boolean ridotto;
}