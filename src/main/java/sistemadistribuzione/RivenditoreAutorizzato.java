package sistemadistribuzione;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rivenditori_autorizzati")

public class RivenditoreAutorizzato extends PuntoVendita {

    @Column(nullable = false)
    private String nome;

}
