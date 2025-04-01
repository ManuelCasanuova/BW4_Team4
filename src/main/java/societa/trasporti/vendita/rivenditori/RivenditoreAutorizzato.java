package societa.trasporti.vendita.rivenditori;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import societa.trasporti.vendita.PuntoVendita;

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
