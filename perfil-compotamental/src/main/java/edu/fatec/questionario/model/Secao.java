package edu.fatec.questionario.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "secao")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Secao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@ManyToOne
	private Questionario questionario;
	
	@Column
	private long indice;
	
	@Column(length=255)
	private String titulo = "";

	@Column(name="nova_pagina")
	@Type(type="yes_no")
	private boolean novaPagina = false;

	@OneToMany(mappedBy = "secao")
	private List<Elemento> elementos = new ArrayList<>();


	public SecaoDTO generateDTO() {
		SecaoDTO cDto = new SecaoDTO();
		cDto.setIndice(this.indice);
		cDto.setTexto(this.titulo);
		List<Elemento> elementos = new ArrayList<>();
		cDto.setRespostas(elementos);
		return cDto;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\tSeção: " + getId());
		sb.append("\tTitulo: " + getTitulo());
		sb.append("\tElementos: \n" + getElementos());
		sb.append("\n");
		return sb.toString();
	}
}
