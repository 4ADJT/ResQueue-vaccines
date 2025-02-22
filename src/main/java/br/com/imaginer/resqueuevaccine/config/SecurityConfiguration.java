package br.com.imaginer.resqueuevaccine.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfiguration {
  private final JwtProperties jwtProperties;

  public SecurityConfiguration(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/eureka/**").permitAll()
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/users/create").permitAll()
            .requestMatchers("/auth/login/").permitAll()
            .requestMatchers("/users/v3/").permitAll()
            .requestMatchers("/swagger-ui/").permitAll()
            .requestMatchers("/webjars/").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 ->
            oauth2.jwt(Customizer.withDefaults())
        );

    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return JwtDecoders.fromIssuerLocation("");
  }
}