package br.com.associadoapi.exceptions.handler;

import br.com.associadoapi.exceptions.AssociadoException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(AssociadoException.class)
    public ResponseEntity<ErrorMessage> catchHttpMessageNotReadableException(AssociadoException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

}
