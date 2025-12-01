package edu.fatec.questionario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "elemento")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Elemento {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	@JsonIgnore
	private Questionario questionario;

	@ManyToOne
	@JoinColumn(name = "secao_id")
	@JsonIgnore
	private Secao secao;

	@Column
	private long indice;

	@JsonIgnore
	@Transient
	private long numeroQuestao;

	@Column(length=30)
	private String tipo = "label";
	
	@Column(length=255)
	private String titulo = "";

	@Column(name="descricao", columnDefinition="TEXT")
	private String descricao = "";

	@Column(length=100)
	private String variavel = "";

	@Column(name="opcoes", columnDefinition="TEXT")
	private String opcoes = "";

	@Column(name="validar")
	@Type(type="yes_no")
	private boolean validar = false;

	@Column(name="validacao_tipo", length=30)
	private String validacaoTipo = "";

	@Column(name="validacao_mensagem", length=255)
	private String validacaoMensagem = "";

	@Column(name="campo_resposta", length=100)
	private String campoResposta = "";

	@Column(name="campo_place_holder", length=100)
	private String campoPlaceHolder = "";

	@Column(name = "espec_precisa")
	@Type(type="yes_no")
	private boolean especialistaPrecisa;

	@Column(name = "espec_texto", length=100)
	private String especialistaTexto = "";

	@Column(name = "espec_variable", length=100)
	private String especialistaVariable = "";

	@Column(name="criado_em")	
	private LocalDateTime criadoEm = LocalDateTime.now();

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t\tElemento: " + getId())
			.append("\tTipo: " + getTipo())
			.append("\tTitulo: " + getTitulo())
			.append("\tValidacao: " + getValidacaoTipo())
			.append("\tValidacao MSG: " + getValidacaoMensagem())
			.append("\n");
		return sb.toString();
	}
}
