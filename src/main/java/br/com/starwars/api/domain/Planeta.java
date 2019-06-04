package br.com.starwars.api.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "planetas")
public class Planeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @Getter
	private String id;

	@Getter @Setter
	private String nome;

	@Getter @Setter
	private String terreno;

	@Getter @Setter
	private String clima;

	@Getter @Setter
	private int aparicoesEmFilmes;

	public Planeta() {
	}

	public Planeta(String nome, String terreno, String clima) {
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
