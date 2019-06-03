package br.com.starwars.api.exception.handler;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MensagemErroCampos implements Serializable {
    private static final long serialVersionUID = 1L;

    private String campo;
    private String mensagem;

    public MensagemErroCampos(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }
}
