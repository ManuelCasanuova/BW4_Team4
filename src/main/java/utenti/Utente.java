package utenti;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("Utenti")
public class Utente extends Persona {

    @Column
    private String tipoUtente; //"Semplice", "Amministratore"

    @OneToOne // Relazione uno-a-uno
    @JoinColumn(name = "numero_tessera")
    private Tessera tessera;
}