package br.com.starwars.api.exception.handler;

import br.com.starwars.api.exception.PlanetaNaoEncontradoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroPadrao> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidacaoErros erros = new ValidacaoErros(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação", "Erro de validação de campos", request.getRequestURI());
        e.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.addErros(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erros);
    }

}
