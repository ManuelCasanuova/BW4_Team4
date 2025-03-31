package sistemadistribuzione;

import lombok.Data;

@Data
public abstract class SistemaEmettitore {

    private Long id;


    public SistemaEmettitore() {}

    public SistemaEmettitore(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
