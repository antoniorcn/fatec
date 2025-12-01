package edu.fatec.questionario.model;

import edu.fatec.questionario.util.QuestionarioUtil;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class QuestionarioResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Questionario questionario = new Questionario();

    @OneToOne
    private Contato contato = new Contato();

    @Transient
    private List<Elemento> elementos = new ArrayList<>();

    @OneToMany(mappedBy = "questionarioResposta")
    private List<Resposta> respostas = new ArrayList<>();

    @Column(name="criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();

    public QuestionarioResposta(Questionario questionario) {
        setQuestionario(questionario);
        for (Elemento e : questionario.getElementos()) {
            getElementos().add(e);
            if (QuestionarioUtil.isPergunta(e)) {
                getRespostas().add(new Resposta(this, e));
            }
        }
    }

    public Resposta respostaDoElemento(Elemento e) {
        for (Resposta resp : getRespostas()) {
            if (e.getIndice() == resp.getIndice()) {
                return resp;
            }
        }
        return null;
    }
}
