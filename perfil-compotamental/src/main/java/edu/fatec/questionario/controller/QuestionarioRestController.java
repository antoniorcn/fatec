package edu.fatec.questionario.controller;

import edu.fatec.questionario.model.Questionario;
import edu.fatec.questionario.model.QuestionarioResposta;
import edu.fatec.questionario.model.Resposta;
import edu.fatec.questionario.services.QuestionarioService;
import edu.fatec.questionario.util.CSVWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class QuestionarioRestController {
    private final QuestionarioService questionarioService;

    @RequestMapping(path="/api/questionario/{questionario}/json", produces = "application/json")
    public List<QuestionarioResposta> questionarioResponsesGetJson(
            @RequestParam("tipo") Optional<String> tipo,
            @PathVariable("questionario") String questionarioNome) throws IOException {
        Questionario questionario = questionarioService.retrieveQuestionarioPorNome(questionarioNome);
        List<QuestionarioResposta> questionarioRespostas = questionarioService.retrieveQuestionarioResposta(questionario);
        return questionarioRespostas;
    }

    @RequestMapping(path="/api/questionario/{questionario}/csv", produces = "text/csv")
    public ResponseEntity questionarioResponsesGetCSV(
            @RequestParam("tipo") Optional<String> tipo,
            @PathVariable("questionario") String questionarioNome) throws IOException {
        Questionario questionario = questionarioService.retrieveQuestionarioPorNome(questionarioNome);
        List<QuestionarioResposta> questionarioRespostas = questionarioService.retrieveQuestionarioResposta(questionario);
        // StringBuilder sb = new StringBuilder();
//        StringWriter sw = new StringWriter();
//        sw.flush();
        List<List<String>> allData = new ArrayList<>();
        if (!questionarioRespostas.isEmpty()) {
//            BufferedWriter bw = new BufferedWriter(sw);
//            CSVPrinter csvPrinter = null;
            for (int i = 0; i < questionarioRespostas.size(); i++) {
                QuestionarioResposta questionarioResposta = questionarioRespostas.get(i);
                if (i == 0) {
                    List<String> variaveis = new ArrayList<>(Arrays.asList("id", "criado_em"));
                    for (Resposta r : questionarioResposta.getRespostas()) {
                        variaveis.add(r.getVariavel());
                    }
                    allData.add(variaveis);
//                    sb.append(String.join(";", variaveis));
//                    sb.append("\n");
//                    try {
//                        csvPrinter = new CSVPrinter(bw,
//                                CSVFormat.TDF
//                                        .withDelimiter(';')
//                                        .withQuote('\'')
//                                        .withRecordSeparator("\r\n")
//                                        .withIgnoreSurroundingSpaces(true)
//                                        .withHeader(variaveis.toArray(new String[0])));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

//                List<String> valores = new ArrayList<>(Arrays.asList(String.valueOf(questionarioResposta.getId()),
//                                                        String.valueOf(questionarioResposta.getCriadoEm())));
//                for (Resposta r : questionarioResposta.getRespostas()) {
//                    valores.add(r.getValor());
//                }
//                sb.append(String.join(";", valores));
//                sb.append("\n");
                //try {
                    List<String> valores = new ArrayList<>(Arrays.asList(String.valueOf(questionarioResposta.getId()),
                            String.valueOf(questionarioResposta.getCriadoEm())));
                    for (Resposta r : questionarioResposta.getRespostas()) {
                        valores.add(r.getValor());
                    }
                    allData.add(valores);
                    //csvPrinter.printRecord(valores);
                    //csvPrinter.flush();
                //} catch (IOException e) {
                //    e.printStackTrace();
               // }
                //sw.append("\n").flush();
            }
        }
        CSVWriter csvw = new CSVWriter();
        List<String> allLines = csvw.generateCSVFromDataLines(allData);
        StringBuffer finalText = new StringBuffer();
        for (String linha : allLines) {
            finalText.append(linha);
            finalText.append("\n");
        }
        //String finalText = sw.toString();
        String finalTextEncoded = new String(finalText.toString().getBytes(), StandardCharsets.ISO_8859_1);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=respostas.csv")
                .header("Content-Type", "text/csv")
                .contentLength(finalTextEncoded.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(finalTextEncoded.getBytes());
    }

    public QuestionarioRestController(QuestionarioService questionarioService) {
        this.questionarioService = questionarioService;
    }
}
