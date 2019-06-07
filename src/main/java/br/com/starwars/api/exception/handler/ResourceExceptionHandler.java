package br.com.starwars.api.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import br.com.starwars.api.exception.PlanetaComNomeDuplicadoException;
import br.com.starwars.api.exception.PlanetaNaoEncontradoException;

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
                .exception(e.getClass().getName())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroPadrao> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidacaoErros erros = new ValidacaoErros(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação", "Erro de validação de campos", request.getRequestURI(), e.getClass().getName());
        e.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.addErros(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erros);
    }
    
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<MensagemErroPadrao> httpClientError(HttpClientErrorException e, HttpServletRequest request) {
    	MensagemErroPadrao msg = MensagemErroPadrao.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Recurso não encontrado")
                .mensagem("Recurso não encontrado na API pública do Star Wars.")
                .caminho(request.getRequestURI())
                .exception(e.getClass().getName())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }
    
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<MensagemErroPadrao> serviceUnavailable(HttpServerErrorException e, HttpServletRequest request) {
    	MensagemErroPadrao msg = MensagemErroPadrao.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .erro("Serviço indisponível")
                .mensagem("Recurso indisponível na API pública do Star Wars.")
                .caminho(request.getRequestURI())
                .exception(e.getClass().getName())
                .build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(msg);
    }

    @ExceptionHandler(PlanetaComNomeDuplicadoException.class)
    public ResponseEntity<MensagemErroPadrao> planetaComNomeDuplicado(PlanetaComNomeDuplicadoException e, HttpServletRequest request) {
        MensagemErroPadrao msg = MensagemErroPadrao.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Nome Duplicado")
                .mensagem(e.getMessage())
                .caminho(request.getRequestURI())
                .exception(e.getClass().getName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
    
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<MensagemErroPadrao>  resourceAccessException(ResourceAccessException e, HttpServletRequest request) {
        MensagemErroPadrao msg = MensagemErroPadrao.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Connection refused")
                .mensagem(e.getMessage())
                .caminho(request.getRequestURI())
                .exception(e.getClass().getName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<MensagemErroPadrao>  resourceAccessException(DuplicateKeyException e, HttpServletRequest request) {
        MensagemErroPadrao msg = MensagemErroPadrao.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Integridade de dados")
                .mensagem("Não é possível inserir informações duplicadas.")
                .caminho(request.getRequestURI())
                .exception(e.getClass().getName())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}
