package br.gov.fatec.comparador.service

import br.gov.fatec.comparador.config.Word2VecProperties
import org.slf4j.LoggerFactory
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import kotlin.math.sqrt
import kotlin.text.Charsets

@Service
class Word2VecTextSimilarityService(
    private val resourceLoader: ResourceLoader,
    private val properties: Word2VecProperties
) {

    private val logger = LoggerFactory.getLogger(Word2VecTextSimilarityService::class.java)

    private val embeddings: Map<String, FloatArray> by lazy { loadEmbeddings() }

    private val dimension: Int
        get() = properties.dimension

    fun similarity(textA: String?, textB: String?): Double {
        val vectorA = embed(textA)
        val vectorB = embed(textB)
        if (vectorA == null || vectorB == null) {
            logger.debug("Nenhum vetor calculado para um dos textos (tokens desconhecidos ou texto vazio).")
            return 0.0
        }
        val cosine = cosineSimilarity(vectorA, vectorB)
        return cosine.coerceIn(0.0, 1.0)
    }

    private fun embed(text: String?): FloatArray? {
        if (text.isNullOrBlank() || embeddings.isEmpty()) {
            return null
        }

        val tokens = tokenize(text)
        if (tokens.isEmpty()) {
            return null
        }

        val accumulator = FloatArray(dimension)
        var hits = 0
        for (token in tokens) {
            val vector = embeddings[token] ?: continue
            for (i in 0 until dimension) {
                accumulator[i] += vector[i]
            }
            hits++
        }

        if (hits == 0) return null

        for (i in accumulator.indices) {
            accumulator[i] /= hits
        }
        return accumulator
    }

    private fun tokenize(text: String): List<String> =
        text
            .lowercase()
            .split(TOKEN_SPLIT_REGEX)
            .mapNotNull { token ->
                val cleaned = token.trim()
                cleaned.takeIf { it.length > 2 }
            }

    private fun cosineSimilarity(a: FloatArray, b: FloatArray): Double {
        var dot = 0.0
        var normA = 0.0
        var normB = 0.0
        for (i in a.indices) {
            dot += a[i] * b[i]
            normA += a[i] * a[i]
            normB += b[i] * b[i]
        }
        if (normA == 0.0 || normB == 0.0) return 0.0
        val similarity = dot / (sqrt(normA) * sqrt(normB))
        return similarity.coerceIn(-1.0, 1.0)
    }

    private fun loadEmbeddings(): Map<String, FloatArray> {
        val resource = resourceLoader.getResource(properties.location)
        require(resource.exists()) {
            "Arquivo Word2Vec não encontrado em ${properties.location}"
        }

        resource.inputStream.bufferedReader(Charsets.UTF_8).use { reader ->
            val vocabulary = mutableMapOf<String, FloatArray>()
            var firstLine = reader.readLine() ?: return emptyMap()

            val headerTokens = firstLine.trim().split(Regex("\\s+"))
            if (headerTokens.size == 2 && headerTokens[0].toLongOrNull() != null && headerTokens[1].toIntOrNull() != null) {
                validateDimension(headerTokens[1].toInt())
                firstLine = reader.readLine()
            } else {
                validateDimension(headerTokens.size - 1)
            }

            parseEmbeddingLine(firstLine, vocabulary)

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line.isNullOrBlank()) continue
                parseEmbeddingLine(line!!, vocabulary)
                if (properties.maxVocabulary > 0 && vocabulary.size >= properties.maxVocabulary) {
                    logger.info("Limite de vocabulário atingido (${properties.maxVocabulary}). Carregamento interrompido.")
                    break
                }
            }

            logger.info("Modelo Word2Vec carregado com ${vocabulary.size} palavras a partir de ${properties.location}.")
            return vocabulary
        }
    }

    private fun parseEmbeddingLine(line: String?, vocabulary: MutableMap<String, FloatArray>) {
        if (line.isNullOrBlank()) return
        val parts = line.trim().split(Regex("\\s+"))
        if (parts.size < dimension + 1) {
            logger.warn("Linha ignorada no arquivo Word2Vec: conteúdo insuficiente.")
            return
        }
        val word = parts[0].lowercase()
        val vector = FloatArray(dimension)
        try {
            for (i in 0 until dimension) {
                vector[i] = parts[i + 1].toFloat()
            }
            vocabulary[word] = vector
        } catch (ex: NumberFormatException) {
            logger.warn("Não foi possível converter a linha para vetor numérico: {}", line, ex)
        }
    }

    private fun validateDimension(dimensionFromFile: Int) {
        require(dimensionFromFile == dimension) {
            "Dimensão do arquivo Word2Vec ($dimensionFromFile) diferente da esperada (${properties.dimension})."
        }
    }
}

private val TOKEN_SPLIT_REGEX = Regex("[^\\p{L}]+")
