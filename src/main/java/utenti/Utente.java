package utenti;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utente extends Persona {

    @Column(name = "tipo_utente")
    private String tipoUtente; // Semplice o Amministratore
}