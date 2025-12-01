package cpb.cursos.controller;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LanguageUtils {
    public static final String STRING_SEPARATOR = "|**|";
    public List<String> getAsStringArray(String expr) {
        System.out.println("Avaliando a expressão: " + expr);
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expr);
        List<String> lista = (List<String>) exp.getValue(List.class);
        return lista;
    }

    public static String consolidateList(List<String> lista) {
        StringBuffer sb = new StringBuffer();
        for (String item : lista) {
            sb.append(item + STRING_SEPARATOR);
        }
        return sb.toString();
    }

    public List<String> getLista() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"Opção 1\", ");
        sb.append("\"Opção 2\", ");
        sb.append("\"Opção 3\", ");
        sb.append("}");
        return getAsStringArray( sb.toString() );
    }
}