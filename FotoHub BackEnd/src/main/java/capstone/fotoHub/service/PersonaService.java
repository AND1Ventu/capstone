package capstone.fotoHub.service;

import capstone.fotoHub.dto.PersonaRequest;
import capstone.fotoHub.exception.NotFoundException;
import capstone.fotoHub.model.Persona;
import capstone.fotoHub.model.Role;
import capstone.fotoHub.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private PasswordEncoder encoder;


    public Page<Persona> getAll(Pageable pageable) {

        return personaRepository.findAll(pageable);
    }

    public Persona getPersonaById(int id) throws NotFoundException {
        return personaRepository.findById(id).orElseThrow(() -> new NotFoundException("Persona con id=" + id + " non trovata"));
    }

    public Persona savePersona(PersonaRequest personaRequest)  {
        Persona persona = new Persona();

        persona.setNome(personaRequest.getNome());
        persona.setCognome(personaRequest.getCognome());
        persona.setBio(personaRequest.getBio());
        persona.setEmail(personaRequest.getEmail());
        persona.setPassword(encoder.encode(personaRequest.getPassword()));
        persona.setRole(Role.UTENTE);
       // sendMail(persona.getEmail());

        return personaRepository.save(persona);
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }

    public Persona updatePersona(int id, PersonaRequest personaRequest) throws NotFoundException {
        Persona p = getPersonaById(id);

        p.setNome(personaRequest.getNome());
        p.setCognome(personaRequest.getCognome());
        p.setBio(personaRequest.getBio());
        p.setEmail(personaRequest.getEmail());

        return personaRepository.save(p);
    }

    public void deletePersona(int id) throws NotFoundException {
        Persona persona = getPersonaById(id);
        personaRepository.delete(persona);
    }


    public Persona getPersonaByMail(String email){
        return personaRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata"));
    }

    public Persona getLoggedInUser() {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getPersonaByMail(loggedInEmail);
    }

}
