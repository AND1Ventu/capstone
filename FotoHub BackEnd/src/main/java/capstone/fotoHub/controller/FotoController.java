package capstone.fotoHub.controller;

import capstone.fotoHub.dto.FotoRequest;
import capstone.fotoHub.exception.NotFoundException;
import capstone.fotoHub.model.CustomResponse;
import capstone.fotoHub.model.Foto;
import capstone.fotoHub.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import com.cloudinary.Cloudinary;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FotoController {
    @Autowired
    private FotoService fotoService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/foto")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), fotoService.getAll(pageable), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/foto/{id}")
    public ResponseEntity<CustomResponse> getFotoById(@PathVariable int id){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), fotoService.getFotoById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/foto/user/{userId}")
    public ResponseEntity<CustomResponse> getUserPhotos(@PathVariable int userId) {
        try {
            // Retrieve photos associated with the user ID
            Page<Foto> userPhotos = fotoService.getUserPhotos(userId);
            return CustomResponse.success(HttpStatus.OK.toString(), userPhotos, HttpStatus.OK);
        } catch (NotFoundException e) {
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return CustomResponse.error("Failed to retrieve user photos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CustomResponse> saveFoto(@RequestParam("upload") MultipartFile file, @RequestHeader("email") String email) {
        if (file.isEmpty()) {
            return CustomResponse.error("No file uploaded", HttpStatus.BAD_REQUEST);
        }

        if (email.isEmpty()){
            return CustomResponse.error("No email uploaded", HttpStatus.BAD_REQUEST);
        }

        try {
            Foto savedFoto = fotoService.saveFoto(file, email);
            return CustomResponse.success(HttpStatus.OK.toString(), savedFoto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return CustomResponse.error("Failed to save photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/foto/{id}")
    public ResponseEntity<CustomResponse> updateFoto(@PathVariable int id, @RequestBody @Validated FotoRequest fotoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), fotoService.updateFoto(id, fotoRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/foto/{id}")
    public ResponseEntity<CustomResponse> deleteFoto(@PathVariable int id){

        fotoService.deleteFoto(id);
        try{
            return CustomResponse.emptyResponse("Foto con id=" + id + " cancellata", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PatchMapping("/foto/{id}/upload")
    public ResponseEntity<CustomResponse> uploadLogo(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {
            Foto foto = fotoService.uploadImmagine(id,
                    (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), foto, HttpStatus.OK);
        }
        catch (IOException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}