package br.com.starwars.api.exception.handler;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidacaoErros extends MensagemErroPadrao {
    private static final long serialVersionUID = 1L;

    @Getter
    private List<MensagemErroCampos> erros = new ArrayList<>();

    public ValidacaoErros(Long timestamp, Integer status, String erro, String mensagem, String caminho) {
        super(timestamp, status, erro, mensagem, caminho);
    }

    public void addErros(String campo, String mensagem) {
        this.erros.add(new MensagemErroCampos(campo, mensagem));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ValidacaoErros that = (ValidacaoErros) o;
        return Objects.equals(erros, that.erros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), erros);
    }
}
