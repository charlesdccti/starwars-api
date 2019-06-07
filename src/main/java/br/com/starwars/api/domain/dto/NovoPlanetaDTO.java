package br.com.starwars.api.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel(description = "Classe que representa as informações obrigatórios para cadastrar um novo planeta")
public class NovoPlanetaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Nome do planeta é obrigatório")
    @ApiModelProperty(notes = "Nome do planeta", required = true, example = "Dagobah")
    private String nome;

    @NotEmpty(message = "Terreno do planeta é obrigatório")
    @ApiModelProperty(notes = "Terreno do planeta", required = true, example = "swamp, jungles")
    private String terreno;

    @NotEmpty(message = "Clima do planeta é obrigatório")
    @ApiModelProperty(notes = "Clima do planeta", required = true, example = "murky")
    private String clima;
}
