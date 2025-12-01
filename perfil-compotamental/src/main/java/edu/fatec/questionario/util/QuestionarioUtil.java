package edu.fatec.questionario.util;

import edu.fatec.questionario.model.Elemento;
import edu.fatec.questionario.model.QuestionarioResposta;
import edu.fatec.questionario.model.Resposta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionarioUtil {

    protected static final List<String> TIPOS_PERGUNTAS =
            Arrays.asList("radioYesNo", "inputText", "areaText", "labelInputText", "labelAreaText", "input",
                    "check", "radio", "select", "list", "grade", "order", "contatoNome", "contatoEmail",
                    "sortItems", "labelInputTextSemNumero", "radioLabelValue");

    private QuestionarioUtil() {
    }

    public static List<Elemento> getApenasPerguntas(List<Elemento> elementos) {
        List<Elemento> perguntas = new ArrayList<>();
        for (Elemento el : elementos) {
            if (TIPOS_PERGUNTAS.contains(el.getTipo())){
                perguntas.add(el);
            }
        }
        return perguntas;
    }

    public static boolean isPergunta(Elemento elemento) {
        return TIPOS_PERGUNTAS.contains(elemento.getTipo());
    }

    public static List<Resposta> procurarRespostaPeloTipo(QuestionarioResposta questResp, String tipo) {
        List<Resposta> respostas = new ArrayList<>();
        for (Resposta resp : questResp.getRespostas()) {
            if (resp.getTipo().equals(tipo)) {
                respostas.add(resp);
            }
        }
        return respostas;
    }

    public static List<Resposta> procurarRespostaPelaVariavel(QuestionarioResposta questResp, String variavel) {
        List<Resposta> respostas = new ArrayList<>();
        for (Resposta resp : questResp.getRespostas()) {
            if (resp.getVariavel().equals(variavel)) {
                respostas.add(resp);
            }
        }
        return respostas;
    }


}
