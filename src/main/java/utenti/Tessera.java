package utenti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utenti.Persona;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tessera extends Persona {

    @Column(name = "numero_tessera", unique = true)
    private String numeroTessera;

    @Column(name = "data_emissione")
    private LocalDate dataDiEmissione;

    @Column(name = "data_scadenza")
    private LocalDate dataDiScadenza;

    public boolean isValid() {
        return LocalDate.now().isBefore(dataDiScadenza);
    }
}