package titoloViaggio.biglietto;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import sistemadistribuzione.SistemaEmettitore;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import titoloViaggio.TitoloViaggio;

import java.time.LocalDate;

@Data
@Entity
@DiscriminatorValue("Biglietti")
public class Biglietto extends TitoloViaggio {

    private boolean vidimato;

    @ManyToOne
    private ParcoMezzi mezzoVidimatore;
    // inserire relazione in ParcoMezzi OneToMany che ritorna una lista di biglietti vidimati  e mappedby "mezzoVidimatore"

    public Biglietto(LocalDate dataEmissione, boolean valido, SistemaEmettitore puntoEmittente, boolean vidimato, ParcoMezzi mezzoVidimatore) {
        super(dataEmissione, valido, puntoEmittente);
        this.vidimato = vidimato;
        this.mezzoVidimatore = mezzoVidimatore;
    }
}