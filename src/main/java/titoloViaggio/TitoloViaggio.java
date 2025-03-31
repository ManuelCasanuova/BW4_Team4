package titoloViaggio;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public abstract class TitoloViaggio {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codiceUnivoco;
    private LocalDate dataEmissione;

}