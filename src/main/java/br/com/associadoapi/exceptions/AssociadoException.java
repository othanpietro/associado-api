package br.com.associadoapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class AssociadoException extends BaseException {

    private final String message;
    public AssociadoException(String message) {
        super(HttpStatus.BAD_REQUEST);
        this.message = message;
    }
}
