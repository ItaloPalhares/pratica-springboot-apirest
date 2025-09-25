package med.voll.apiRest.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404(){
        return ResponseEntity.notFound().build();

    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<String> tratarErroValidacaoConsultas(ValidacaoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
