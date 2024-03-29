package capstone.fotoHub.service;

import capstone.fotoHub.dto.FotoRequest;
import capstone.fotoHub.exception.NotFoundException;
import capstone.fotoHub.model.Foto;
import capstone.fotoHub.model.Persona;
import capstone.fotoHub.repository.FotoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;


import java.io.IOException;

@Service
public class FotoService {

    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    @Value("${cloudinary.api_key}")
    private String apiKey;

    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    private PersonaService personaService;

    public Page<Foto> getAll(Pageable pageable){
        return fotoRepository.findAll(pageable);
    }

    public Foto getFotoById(int id) throws NotFoundException {
        return fotoRepository.findById(id).orElseThrow(() -> new NotFoundException("Foto con id=" + id + " non trovata"));
    }

    public Foto saveFoto(MultipartFile file, String email) throws NotFoundException {
        if (file.isEmpty()) {
            throw new NotFoundException("No file uploaded");
        }

        try {
            // Upload the file to Cloudinary
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret
            ));
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            Persona loggedInUser = personaService.getPersonaByMail(email);

            System.out.println(loggedInUser);
            // Save the URL to the Foto entity
            Foto foto = new Foto();
            foto.setImmagine(file.getOriginalFilename());
            foto.setUrl(imageUrl);
            foto.setPersona(loggedInUser);

            return fotoRepository.save(foto);
        } catch (IOException e) {
            throw new NotFoundException("Error uploading file");
        }
    }

    public Foto updateFoto(int id, FotoRequest fotoRequest) throws NotFoundException {
        Foto foto = getFotoById(id);

        Persona persona = personaService.getPersonaById(fotoRequest.getIdPersona());

        foto .setNome(fotoRequest.getNome());
        foto.setValutazione(fotoRequest.getValutazione());
        foto .setPersona(persona);

        return fotoRepository.save(foto );

    }

    public void deleteFoto(int id) throws NotFoundException {
        Foto foto = getFotoById(id);

        fotoRepository.delete(foto );
    }

    public Foto uploadImmagine(int id, String url) throws NotFoundException{
        Foto foto = getFotoById(id);
        foto.setImmagine(url);
        return fotoRepository.save(foto );
    }


    public Page<Foto> getUserPhotos(int userId) throws NotFoundException {
        return fotoRepository.findByPersonaId(userId, PageRequest.of(0, 12));
    }

}
