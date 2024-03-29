package capstone.fotoHub.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class CustomResponse {
    private String message;
    private LocalDateTime dateResponse;
    private Object response;

    public CustomResponse(String message, Object response) {
        this.message = message;
        this.response = response;
        dateResponse = LocalDateTime.now();
    }

    public CustomResponse(String message) {
        this.message = message;
        dateResponse = LocalDateTime.now();
    }

    public static ResponseEntity<CustomResponse> emptyResponse(String message, HttpStatus httpStatus){
        CustomResponse personaResponse = new CustomResponse(message);
        return new ResponseEntity<>(personaResponse, httpStatus);
    }

    public static ResponseEntity<CustomResponse> success(String message, Object obj, HttpStatus httpStatus){
        CustomResponse personaResponse = new CustomResponse(message, obj);
        return new ResponseEntity<>(personaResponse, httpStatus);
    }
    public static ResponseEntity<CustomResponse> success(Object obj, HttpStatus httpStatus){
        CustomResponse personaResponse = new CustomResponse(httpStatus.toString(), obj);
        return new ResponseEntity<>(personaResponse, httpStatus);
    }
    public static ResponseEntity<CustomResponse> error(String message, HttpStatus httpStatus){
        CustomResponse personaResponse = new CustomResponse(message);
        return new ResponseEntity<>(personaResponse, httpStatus);
    }
    public static ResponseEntity<CustomResponse> error(HttpStatus httpStatus) {
        CustomResponse personaResponse = new CustomResponse(httpStatus.toString());
        return new ResponseEntity<>(personaResponse, httpStatus);
    }
}
