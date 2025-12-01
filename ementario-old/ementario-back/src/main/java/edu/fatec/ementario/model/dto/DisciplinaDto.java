package edu.fatec.ementario.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.fatec.ementario.model.Curso;
import edu.fatec.ementario.model.Faculdade;

import java.io.Serializable;

public class DisciplinaDto implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long id;
	public String nome;
	public String objetivo;
	public String ementa;
	public String metodologiaProposta;
	public String instrumentosAvaliacao;
	public String bibliografiaBasica;
	public String bibliografiaComplementar;
	public String descricao;
	public Faculdade faculdade;
	public Curso curso;

	@JsonProperty("faculdade")
	private void unpackNestedFaculdade(Long faculdadeId) {
		this.faculdade = new Faculdade();
		faculdade.setId(faculdadeId);
	}

	@JsonProperty("curso")
	private void unpackNestedCurso(Long cursoId) {
		this.curso = new Curso();
		curso.setId(cursoId);
	}
}
