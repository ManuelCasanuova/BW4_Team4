package societa.trasporti.titoloViaggio.abbonamento;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.titoloViaggio.TitoloViaggio;
import societa.trasporti.utenti.Tessera;
import societa.trasporti.vendita.PuntoVendita;

import java.time.LocalDate;

@Data
@Entity
@DiscriminatorValue("ABBONAMENTO")
@NoArgsConstructor
public class Abbonamento extends TitoloViaggio {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera;


    public Abbonamento(Long codiceUnivoco, double prezzoViaggio, LocalDate dataAcquisto, PuntoVendita puntoVendita, TipoAbbonamento tipoAbbonamento, Tessera tessera, LocalDate dataInizio) {
        super(codiceUnivoco, prezzoViaggio, dataAcquisto, puntoVendita);
        this.tipoAbbonamento = tipoAbbonamento;
        this.dataInizio = dataInizio;
        if(tipoAbbonamento == TipoAbbonamento.SETTIMANALE) this.dataFine = dataInizio.plusWeeks(1);
        else if(tipoAbbonamento == TipoAbbonamento.MENSILE) this.dataFine = dataInizio.plusMonths(1);
        else if (tipoAbbonamento == TipoAbbonamento.SEMESTRALE) this.dataFine = dataInizio.plusMonths (6);
        else this.dataFine= dataInizio.plusYears(1);
        this.tessera = tessera;
    }
}