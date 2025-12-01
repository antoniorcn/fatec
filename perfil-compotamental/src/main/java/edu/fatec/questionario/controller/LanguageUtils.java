package edu.fatec.questionario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, String> getAsMapFromJSON(String expr) {
        // System.out.println("Avaliando o JSON: " + expr);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> map = mapper.readValue(expr, Map.class);
            return map;
        } catch (Exception e) {
            System.err.println("Erro avaliando a expressão: " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static String consolidateList(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String item : lista) {
            sb.append(item).append(STRING_SEPARATOR);
        }
        return sb.toString();
    }

    public List<String> getLista() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"Opção 1\", ");
        sb.append("\"Opção 2\", ");
        sb.append("\"Opção 3\", ");
        sb.append("}");
        return getAsStringArray( sb.toString() );
    }
}