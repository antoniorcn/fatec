package edu.fatec.ementario.model;

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
public class Faculdade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(columnDefinition="TEXT")
	private String descricao;

	@Column(nullable = false, length = 30)
	private String tipo;

	@Lob
	private byte[] imagem;
}
