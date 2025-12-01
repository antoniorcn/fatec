package edu.fatec.ementario.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.fatec.ementario.model.Faculdade;

import java.io.Serializable;

public class CursoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long id;
	public String nome;
	public String descricao;
	public Faculdade faculdade;
	public String tipo;

	@JsonProperty("faculdade")
	private void unpackNested(Long faculdadeId) {
		this.faculdade = new Faculdade();
		faculdade.setId(faculdadeId);
	}
}
