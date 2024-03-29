package capstone.fotoHub.controller;

import capstone.fotoHub.dto.LoginRequest;
import capstone.fotoHub.dto.PersonaRequest;
import capstone.fotoHub.exception.BadRequestException;
import capstone.fotoHub.exception.LoginFaultException;
import capstone.fotoHub.model.Persona;
import capstone.fotoHub.security.JwtTools;
import capstone.fotoHub.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private PersonaService personaService;
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/auth/register")
    public Persona register(@RequestBody @Validated PersonaRequest personaRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return personaService.savePersona(personaRequest);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        Persona persona = personaService.getPersonaByMail(loginRequest.getEmail());

        if(encoder.matches(loginRequest.getPassword(), persona.getPassword())){
            String token = jwtTools.createToken(persona);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("email", loginRequest.getEmail()); // Add email to the response
            return ResponseEntity.ok(response);
        } else {
            throw new LoginFaultException("Username/Password errate");
        }
    }

}