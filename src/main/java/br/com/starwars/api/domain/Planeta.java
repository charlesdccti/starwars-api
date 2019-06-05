package br.com.starwars.api.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "planetas")
public class Planeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Indexed
	@Id @Getter @Setter
	private String id;

	
	@Indexed(unique=true)
	@Getter @Setter
	private String nome;

	@Getter @Setter
	private String terreno;

	@Getter @Setter
	private String clima;

	@Getter @Setter
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
