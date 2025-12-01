package edu.fatec.questionario.services;

import edu.fatec.questionario.model.Questionario;
import edu.fatec.questionario.model.QuestionarioResposta;
import edu.fatec.questionario.model.Resposta;
import edu.fatec.questionario.repository.RespostaRepository;
import edu.fatec.questionario.repository.ContatoRepository;
import edu.fatec.questionario.repository.QuestionarioRepository;
import edu.fatec.questionario.repository.QuestionarioRespostaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionarioService {
    private final QuestionarioRepository questionarioRepository;
    private final QuestionarioRespostaRepository questionarioRespostaRepository;
    private final ContatoRepository contatoRepository;
    private final RespostaRepository respostaRepository;


    public QuestionarioService(QuestionarioRepository questionarioRepository,
                               QuestionarioRespostaRepository questionarioRespostaRepository,
                               ContatoRepository contatoRepository,
                               RespostaRepository respostaRepository) {
        this.questionarioRepository = questionarioRepository;
        this.questionarioRespostaRepository = questionarioRespostaRepository;
        this.contatoRepository = contatoRepository;
        this.respostaRepository = respostaRepository;
    }

    public Questionario retrieveQuestionarioPorNome(String questionarioNome) {
        return questionarioRepository.findByNome(questionarioNome);
    }

    public List<QuestionarioResposta> retrieveQuestionarioResposta(Questionario questionario) {
        return questionarioRespostaRepository.getAllQuestionarioRespostas(questionario);
    }

    public void salvarQuestionarioRespostas(QuestionarioResposta questResp) {

        long questId = questResp.getQuestionario().getId();
        Optional<Questionario> questionario = questionarioRepository.findById(questId);
        System.out.println("Questionario com id: " + questId + "  Localizado: " + questionario.isPresent());
        if (questionario.isPresent()) {
            questResp.setQuestionario(questionario.get());
            contatoRepository.save(questResp.getContato());
            questionarioRespostaRepository.save(questResp);
            for (Resposta resp : questResp.getRespostas()) {
                respostaRepository.save(resp);
            }
        }
    }
}
