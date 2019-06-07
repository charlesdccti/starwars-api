package br.com.starwars.api.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import com.mongodb.lang.NonNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "planetas")
@ApiModel(description = "Planeta a ser cadastrado pelo API na base de dados")
public class Planeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Indexed
	@Id @Getter @Setter
	@ApiModelProperty(notes = "Id gerado pelo mongodb para o planeta", example = "5cfa4d890274390026f1f635")
	private String id;

	
	@Indexed(unique=true)
	@Getter @Setter
	@ApiModelProperty(notes = "Nome do planeta", required = true, example = "Dagobah")
	private String nome;

	@Getter @Setter
	@ApiModelProperty(notes = "Terreno do planeta", required = true, example = "swamp, jungles")
	private String terreno;

	@Getter @Setter
	@ApiModelProperty(notes = "Clima do planeta", required = true, example = "murky")
	private String clima;

	@Getter @Setter
	@ApiModelProperty(notes = "Aparições em filmes", example = "3")
	private int aparicoesEmFilmes;

	public Planeta(@NonNull String nome, @NonNull String terreno, @NonNull String clima) {
		Assert.noNullElements(new Object[] { nome, terreno, clima }, "O construtor não deve receber informações nulas.");

		Assert.hasLength(nome, "O nome não pode estar vázio.");
		Assert.hasLength(terreno, "O terreno não pode estar vázio.");
		Assert.hasLength(clima, "O clima não pode estar vázio.");
				
		this.nome = nome;
		this.terreno = terreno;
		this.clima = clima;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Planeta planeta = (Planeta) o;
		return Objects.equals(id, planeta.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
