package br.com.alfser.projeto.pagamentos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPIDoc() {
        return new OpenAPI()
                .info(new Info().title("API de Pagamento")
                        .description(
                                "API de Genenciamento de Pagamentos.")
                        .version("v1"));
    }

}
