package com.sergio.SIGEV_MPF.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIGEV-MPF API")
                        .version("1.0.0")
                        .description("API de gestão do sistema SIGEV-MPF")
                        .contact(new Contact()
                                .name("Sérgio Souza")
                                .email("sergio.tbl0123@gmail.com")));
    }
}