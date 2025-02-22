package br.com.imaginer.resqueuevaccine.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(new Info()
        .title("User API Service")
        .description("User API Service")
        .version("1.0.0"))
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                ))
                .servers(List.of(
                    new Server().url("http://localhost:8080").description("Localhost (Desenvolvimento)"),
                    new Server().url("https://gateway.imaginer.com.br").description("Servidor de Produção")
                ));
  }
}