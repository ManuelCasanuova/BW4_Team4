package titoloViaggio.abbonamento;

import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import utenti.Utente;

import java.time.LocalDate;

public class Abbonamento {
    //
    @Enumerated
    private TipoAbbonamento tipoAbbonamento;

    private LocalDate dataInizio;

    private LocalDate dataFine;

    @OneToOne
    private Utente utente;

}
