package societatrasporti.classi.vendita.rivenditori;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import societatrasporti.classi.vendita.PuntoVendita;

@Entity
@Data
@NoArgsConstructor
@Table(name="rivenditori_autorizzati")
public class RivenditoreAutorizzato extends PuntoVendita {

    @Column(nullable = false)
    private String nome;

    public RivenditoreAutorizzato(String indirizzo, String nome) {
        super(indirizzo);
        this.nome = nome;
    }

}
