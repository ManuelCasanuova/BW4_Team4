package societa.trasporti.titoloViaggio.biglietto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.exception.NotFoundException;
import societa.trasporti.parchiMezzi.ParcoMezzi;
import societa.trasporti.titoloViaggio.TitoloViaggio;
import societa.trasporti.utenti.Utente;
import societa.trasporti.vendita.PuntoVendita;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor

public class Biglietto extends TitoloViaggio {

    @Column(nullable = false)
    private boolean convalidato = false;


    @Column(name = "data_convalida")
    private LocalDate dataConvalida;


    @ManyToOne
    @JoinColumn(name = "veicolo_pubblico")
    private ParcoMezzi parcoMezzi;

    public Biglietto(Long codiceUnivoco, double prezzoViaggio, LocalDate dataAquisto, PuntoVendita puntoVendita) {
        super(codiceUnivoco, prezzoViaggio, dataAquisto, puntoVendita);
    }
}