package edu.fatec.questionario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resposta")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	@JsonIgnore
	private QuestionarioResposta questionarioResposta;

	@Column
	private long indice;

	@Column(length=30)
	private String tipo = "inputText";

	@Column(length=255)
	private String titulo = "";

	@Column(length=100)
	private String variavel = "";

	@Column(name="valor", columnDefinition="TEXT")
	private String valor = "";

	@Column(name="criado_em")
	private LocalDateTime criadoEm = LocalDateTime.now();

	public Resposta(QuestionarioResposta questionarioResposta, Elemento e) {
		this.setTipo(e.getTipo());
		this.setTitulo(e.getTitulo());
		this.setVariavel(e.getVariavel());
		this.setIndice(e.getIndice());
		this.setQuestionarioResposta(questionarioResposta);

	}
}
