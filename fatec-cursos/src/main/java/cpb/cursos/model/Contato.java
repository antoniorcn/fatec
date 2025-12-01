package cpb.cursos.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contato")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Contato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(length=150)
	private String nome = "";
	
	@Column(length=150)
	private String email = "";
	
	@Column(length=100)
	private String telefone = "";
	
	@Column(name = "tel_tipo", length=30)
	private String telTipo = "";
	
	@Column(length=150)
	private String endereco = "";
	
	@Column(length=50)
	private String bairro = "";

	@Column(length=50)
	private String cidade = "";
	
	@Column(length=2)
	private String estado= "";
	
	@Column(length=9)
	private String cep = "";
	
	@Column(length=50)
	private String escolaridade = "";
	
	@Column(name="ultima_instituicao", length=100)
	private String ultimaInstituicao = "";
	
	@Column(name="empresa_nome", length=100)
	private String empresaNome = "";
	
	@Column(name="criado_em")
	private LocalDateTime criadoEm = LocalDateTime.now();
}
