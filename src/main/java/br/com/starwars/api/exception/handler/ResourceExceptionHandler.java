package br.com.starwars.api.exception.handler;

import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(PlanetaNaoEncontradoException.class)
    public ResponseEntity<MensagemErroPadrao> planetaNaoEncontrado(PlanetaNaoEncontradoException e, HttpServletRequest request) {
        MensagemErroPadrao msg = MensagemErroPadrao.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Planeta não encontrado")
                .mensagem(e.getMessage())
                .caminho(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }
}
