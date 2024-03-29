package capstone.fotoHub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email obbligatorio")
    private String email;
    @NotBlank(message = "Password obbligatoria")
    private String password;
}