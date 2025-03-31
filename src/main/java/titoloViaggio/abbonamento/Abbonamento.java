package titoloViaggio.abbonamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistemadistribuzione.SistemaEmettitore;
import titoloViaggio.TitoloViaggio;
import utenti.Tessera;
import utenti.Utente;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Abbonamenti")
public class Abbonamento extends TitoloViaggio {

    @Enumerated
    private TipoAbbonamento tipoAbbonamento;

    private LocalDate dataInizio;
    private LocalDate dataFine;

    @OneToOne
    private Tessera tessera;
    // inserire relazione @OneToOne su tessera e il mappedby

    @ManyToOne
    private Utente utente;
    // inserire relazione @OneToMany su utente con mappedby e che ritorni una lista di abbonamenti

    public Abbonamento(LocalDate dataEmissione, boolean valido, SistemaEmettitore puntoEmittente, TipoAbbonamento tipoAbbonamento, LocalDate dataInizio, LocalDate dataFine, Utente utente) {
        super(dataEmissione, valido, puntoEmittente);
        this.tipoAbbonamento = tipoAbbonamento;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.utente = utente;
    }

}
