package societa.trasporti.exception;

import java.time.LocalDate;

public class RangeDateErrato extends RuntimeException {
    public RangeDateErrato(LocalDate dataInizioPeriodo, LocalDate  dataFinePeriodo) {
        super("L'intervallo date non è valido: la data di Inizio " + dataInizioPeriodo + "  non può essere successiva all data di Fine periodo " + dataFinePeriodo);
    }
}
