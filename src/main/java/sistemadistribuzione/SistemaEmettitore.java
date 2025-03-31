package sistemadistribuzione;

import jakarta.persistence.*;
import lombok.Data;
import titoloViaggio.TitoloViaggio;
import java.util.List;

@Data
@Entity
@Table(name = "sistemi_emettitori")
public abstract class SistemaEmettitore {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "sistemaEmettitore", cascade = CascadeType.ALL) // Relazione 1-N
    private List<TitoloViaggio> titoliEmessi; // Lista dei titoli emessi dal sistema

    public SistemaEmettitore() {}

    public SistemaEmettitore(Long id) {
        this.id = id;
    }
}