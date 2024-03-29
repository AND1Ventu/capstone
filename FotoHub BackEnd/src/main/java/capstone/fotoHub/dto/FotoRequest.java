package capstone.fotoHub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FotoRequest {
    @NotNull(message = "Nome obbligatorio")
    @NotEmpty(message = "Nome non vuoto")
    private String nome;

    @NotNull(message = "valutazione obbligatoria")
    @NotEmpty(message = "valutazione non vuota")
    private int valutazione;

    @NotNull(message = "url obbligatoria")
    @NotEmpty(message = "url non vuota")
    private String url;

    @NotNull(message = "Persona obbligatoria")
    private Integer idPersona;
}
