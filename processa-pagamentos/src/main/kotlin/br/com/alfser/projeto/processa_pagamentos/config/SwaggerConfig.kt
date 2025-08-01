package br.com.alfser.projeto.processa_pagamentos.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPIDoc(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Serviço de Processamento de Pagamento")
                    .description(
                        "Serviço de Processamento de Pagamentos."
                    )
                    .version("v1")
            )
    }
}