package cpb.cursos.util;

import cpb.cursos.model.Elemento;
import cpb.cursos.model.QuestionarioResposta;
import cpb.cursos.model.Resposta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionarioUtil {

    protected static final List<String> TIPOS_PERGUNTAS =
            Arrays.asList("radioYesNo", "inputText", "areaText", "labelInputText", "labelAreaText", "input",
                    "check", "radio", "select", "list", "grade", "order", "contatoNome", "contatoEmail",
                    "sortItems", "labelInputTextSemNumero");

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
}
