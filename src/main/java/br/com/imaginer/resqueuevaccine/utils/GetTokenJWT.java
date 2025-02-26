package br.com.imaginer.resqueuevaccine.utils;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class GetTokenJWT {

  private GetTokenJWT() {
    throw new IllegalStateException("Utility class");
  }

  public static UUID obtainUserId(Jwt jwt) {
    return UUID.fromString(jwt.getSubject());
  }

  public static String obtainClaimEmail(Jwt jwt) {
    return jwt.getClaim("email");
  }
}
