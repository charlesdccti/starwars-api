package br.com.starwars.api.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class NovoPlanetaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Nome do planeta é obrigatório")
    private String nome;

    @NotEmpty(message = "Terreno do planeta é obrigatório")
    private String terreno;

    @NotEmpty(message = "Clima do planeta é obrigatório")
    private String clima;
}
