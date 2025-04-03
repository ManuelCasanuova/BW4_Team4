package societa.trasporti.classiAggiuntive.rifornimento;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rifornimenti")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Rifornimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_rifornimento")
    private TipoRifornimento tipoRifornimento;

    @Column(name = "quantita")
    private int quantita;

    @Column(name = "costo")
    private double costo;

    @Column(name = "data_rifornimento")
    private LocalDate dataRifornimento;

    public Rifornimento(TipoRifornimento tipoRifornimento, int quantita, double costo, LocalDate dataRifornimento) {
        this.tipoRifornimento = tipoRifornimento;
        this.quantita = quantita;
        this.costo = costo;
        this.dataRifornimento = dataRifornimento;
    }
}
