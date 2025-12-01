package edu.fatec.ementario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(columnDefinition="TEXT")
	private String objetivo;

	@Column(columnDefinition="TEXT")
	private String ementa;

	@Column(columnDefinition="TEXT")
	private String metodologiaProposta;

	@Column(columnDefinition="TEXT")
	private String instrumentosAvaliacao;

	@Column(columnDefinition="TEXT")
	private String bibliografiaBasica;

	@Column(columnDefinition="TEXT")
	private String bibliografiaComplementar;

	@Column(columnDefinition="TEXT")
	private String descricao;

	@Column
	private int cargaHoraria;

	@ManyToOne
	@JoinColumn(name = "faculdade_id")
	@JsonBackReference("faculdade")
	private Faculdade faculdade;

	@ManyToOne
	@JoinColumn(name = "curso_id")
	@JsonBackReference("curso")
	private Curso curso;
}
