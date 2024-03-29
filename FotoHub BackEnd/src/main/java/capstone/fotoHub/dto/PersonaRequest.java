package capstone.fotoHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonaRequest {
    @NotNull(message = "nome obbligatorio")
    @NotEmpty(message = "nome non vuoto")
    private String nome;
    @NotNull(message = "cognome obbligatorio")
    @NotEmpty(message = "cognome non vuoto")
    private String cognome;
    @Email(message = "Inserire email valida")
    private String email;

    private String bio;
    @NotNull(message = "password obbligatorio")
    @NotEmpty(message = "password non vuoto")
    private String password;
}
