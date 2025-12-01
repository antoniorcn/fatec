package br.gov.fatec.comparador.service

import br.gov.fatec.comparador.config.Word2VecProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.io.DefaultResourceLoader

class Word2VecTextSimilarityServiceTest {

    private fun service(): Word2VecTextSimilarityService {
        val props = Word2VecProperties().apply {
            location = "classpath:embeddings/sample-word2vec.txt"
            dimension = 3
            maxVocabulary = 0
        }
        return Word2VecTextSimilarityService(DefaultResourceLoader(), props)
    }

    @Test
    fun `should return similarity between 0 and 1`() {
        val score = service().similarity("projeto de software", "gestao de projetos e tecnologia")
        assertTrue(score in 0.0..1.0)
    }

    @Test
    fun `similarity is zero when no shared embeddings`() {
        val score = service().similarity("palavra desconhecida", "outra desconhecida")
        assertEquals(0.0, score)
    }
}
