package br.com.imaginer.resqueuevaccine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
@Getter
@Setter
public class JwtProperties {
  private String issuerUri;
}