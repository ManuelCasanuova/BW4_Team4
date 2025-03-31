package societa.trasporti.parchiMezzi;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mezzi")



public abstract class ParcoMezzi {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long matricola;

   @Enumerated(EnumType.STRING)
    private TipoVeicolo tipoVeicolo;

    private int capienza;
    private boolean inServizio;
}

