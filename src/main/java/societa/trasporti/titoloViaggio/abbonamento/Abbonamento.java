package societa.trasporti.titoloViaggio.abbonamento;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import societa.trasporti.titoloViaggio.TitoloViaggio;
import societa.trasporti.utenti.Tessera;
import societa.trasporti.utenti.Utente;
import societa.trasporti.vendita.PuntoVendita;

import java.time.LocalDate;


@Entity
@Table(name="abbonamenti")


public class Abbonamento extends TitoloViaggio {


    @Column(name = "tipo_abbonamento", nullable = false)
    @Enumerated
    private TipoAbbonamento tipoAbbonamento;


    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera;

    public Abbonamento(Long codiceUnivoco, double prezzoViaggio, LocalDate dataAquisto, PuntoVendita puntoVendita, TipoAbbonamento tipoAbbonamento, LocalDate dataInizio, Tessera tessera) {
        super(codiceUnivoco, prezzoViaggio, dataAquisto, puntoVendita);
        this.dataInizio = dataInizio;
        if (tipoAbbonamento == TipoAbbonamento.SETTIMANALE) this.dataFine = dataInizio.plusDays(7);
        else if (tipoAbbonamento == TipoAbbonamento.MENSILE) this.dataFine = dataInizio.plusMonths(1);
        else if (tipoAbbonamento== tipoAbbonamento.SETTIMANALE) this.dataFine = dataInizio.plusMonths(6);
        else this.dataFine = dataInizio.plusYears(1);
        this.tessera = tessera;
        this.tipoAbbonamento = tipoAbbonamento;
    }
}
