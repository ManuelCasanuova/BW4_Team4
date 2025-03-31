package sistemadistribuzione;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rivenditori_autorizzati")
public class RivenditoreAutorizzato extends SistemaEmettitore {

    private String nome;

    public RivenditoreAutorizzato(Long id, String nome) {
        super(id);
        this.nome = nome;
    }
}
