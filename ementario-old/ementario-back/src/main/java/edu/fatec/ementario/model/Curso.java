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
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(columnDefinition="TEXT")
	private String descricao;

	@Column
	private int ano;

	@Column
	private int semestre;

	@ManyToOne
	@JoinColumn(name = "faculdade_id")
	@JsonBackReference("faculdade")
	private Faculdade faculdade;

	@Column(nullable = false, length = 30)
	private String tipo;
}
