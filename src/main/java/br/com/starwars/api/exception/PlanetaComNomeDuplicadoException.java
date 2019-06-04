package br.com.starwars.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlanetaComNomeDuplicadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PlanetaComNomeDuplicadoException(String message) {
        super(message);
    }
}
