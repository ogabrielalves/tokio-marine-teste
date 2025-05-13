package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transfer API")
                        .contact(new Contact()
                                .name("Gabriel Alves")
                                .email("gabrielalvessilva02@outlook.com"))
                        .license(new License()
                                .name("ogabrielalves")
                                .url("https://github.com/ogabrielalves")));
    }
}
