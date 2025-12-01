package edu.fatec.questionario.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "questionario")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Questionario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(length=100)
	private String nome;

	@Column(length=255)
	private String titulo;

	@Column(name="descricao", columnDefinition="TEXT")
	private String descricao;

	@OneToMany(mappedBy = "questionario")
	@JsonIgnore
	private List<Secao> secoes = new ArrayList<>();

	@OneToMany(mappedBy = "questionario")
	private List<Elemento> elementos = new ArrayList<>();

	@Column(name="criado_em")
	private LocalDateTime criadoEm = LocalDateTime.now();

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Questionario: " + getId());
		sb.append("\tTitulo: " + getTitulo());
		sb.append("\n");
		return sb.toString();
	}

}
