package br.gov.fatec.comparador.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@ConfigurationProperties(prefix = "app.cors")
data class CorsProperties(
    var allowedOrigins: List<String> = listOf("*"),
    var allowedMethods: List<String> = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"),
    var allowedHeaders: List<String> = listOf("*"),
    var allowCredentials: Boolean = false,
    var maxAgeSeconds: Long = 3600
)

@Configuration
@EnableConfigurationProperties(CorsProperties::class)
class CorsConfig(
    private val corsProperties: CorsProperties
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").apply {
            allowedOrigins(*corsProperties.allowedOrigins.toTypedArray())
            allowedMethods(*corsProperties.allowedMethods.toTypedArray())
            allowedHeaders(*corsProperties.allowedHeaders.toTypedArray())
            allowCredentials(corsProperties.allowCredentials)
            maxAge(corsProperties.maxAgeSeconds)
        }
    }
}
