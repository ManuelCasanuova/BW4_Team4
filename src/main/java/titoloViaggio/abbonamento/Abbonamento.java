package titoloViaggio.abbonamento;

import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class Abbonamento {
    @Enumerated
    private TipoAbbonamento tipoAbbonamento;

    private LocalDate dataInizio;
    private LocalDate dataFine;


}
