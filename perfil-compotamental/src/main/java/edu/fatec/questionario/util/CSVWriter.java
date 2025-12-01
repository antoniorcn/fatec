package edu.fatec.questionario.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWriter {
    public String convertToCSV(List<String> data) {

        return Stream.of(data.toArray(new String[]{}))
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(";"));
    }

    public List<String> generateCSVFromDataLines(List<List<String>> dataLines) throws IOException {
        ByteArrayOutputStream inMemoryStream = new ByteArrayOutputStream();
        OutputStreamWriter outw = new OutputStreamWriter(inMemoryStream);

        try (PrintWriter pw = new PrintWriter(outw)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inMemoryStream.toByteArray());
        BufferedReader bufr = new BufferedReader(new InputStreamReader(inputStream));
        List<String> linhas = new ArrayList<>();
        while (bufr.ready()) {
            linhas.add(bufr.readLine());
        }
        return linhas;
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(";") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static void main(String[] args) throws IOException {
        List<String> header = Arrays.asList("H1", "H2", "H3", "H4");
        List<String> linha1 = Arrays.asList("A", "B", "C", "D");
        CSVWriter csvw = new CSVWriter();
        List<List<String>> lista = Arrays.asList(header, linha1);
        List<String> linhas = csvw.generateCSVFromDataLines(lista);
        System.out.println("CSV Writer Teste");
        System.out.println(linhas);

    }
}
