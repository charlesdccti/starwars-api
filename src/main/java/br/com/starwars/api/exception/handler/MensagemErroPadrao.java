package br.com.starwars.api.exception.handler;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MensagemErroPadrao implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String caminho;

    public MensagemErroPadrao(Long timestamp, Integer status, String erro,
                              String mensagem, String caminho) {
        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }
}
