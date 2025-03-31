package utenti;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@DiscriminatorValue("Tessere")
public class Tessera extends Persona {

    @Column
    private String numeroTessera;

    @Column
    private LocalDate dataDiEmissione;

    @Column
    private LocalDate dataDiScadenza;

    @OneToOne(mappedBy = "tessera")
    private Utente utente;

    public boolean isValid() {
        return LocalDate.now().isBefore(dataDiScadenza);
    }
}