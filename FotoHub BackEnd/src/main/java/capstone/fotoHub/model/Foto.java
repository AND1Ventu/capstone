package capstone.fotoHub.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String immagine;
    private String nome;
    private int valutazione;
    private String url;


    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
