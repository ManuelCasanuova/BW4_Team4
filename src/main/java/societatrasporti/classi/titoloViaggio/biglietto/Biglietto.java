package societatrasporti.classi.titoloViaggio.biglietto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societatrasporti.classi.parchiMezzi.ParcoMezzi;
import societatrasporti.classi.titoloViaggio.TitoloViaggio;
import societatrasporti.classi.vendita.PuntoVendita;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "biglietti")

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