package br.gov.fatec.comparador.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.embeddings.word2vec")
class Word2VecProperties {
    /**
     * Caminho para o arquivo Word2Vec. Aceita prefixos do Spring como `classpath:` ou `file:`.
     */
    var location: String = "classpath:embeddings/sample-word2vec.txt"

    /**
     * Quantidade de dimensões (tamanho dos vetores) esperada no arquivo.
     */
    var dimension: Int = 300

    /**
     * Limite máximo de palavras carregadas (0 = todas).
     */
    var maxVocabulary: Int = 0
}
