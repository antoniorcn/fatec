package edu.fatec.questionario.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SecaoDTO {
	private long id;
	
	private long indice;
	
	private String texto = "";
	
	private List<Elemento> respostas = new ArrayList<>();
}
