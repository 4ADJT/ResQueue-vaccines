package br.com.imaginer.resqueuevaccine.controllers;

import br.com.imaginer.resqueuevaccine.ms.ClientPublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/vaccine/message")
@Tag(name = "Message", description = "User notification")
public class MessageController {
  private final ClientPublisherService message;

  public MessageController(ClientPublisherService message) {
    this.message = message;
  }

  @Operation(hidden = true , summary = "Use message service", security = { @SecurityRequirement(name = "bearer-key") })
  @GetMapping("/message/{messageText}")
  public ResponseEntity<Void> sendMessage(
      @PathVariable String messageText,
      @AuthenticationPrincipal Jwt jwt
  ) {

    UUID userId = UUID.fromString(jwt.getSubject());
    String userEmailFromToken = jwt.getClaim("email");

    log.info("Usuário autenticado: " + userId + " - " + userEmailFromToken);

    if (messageText.isEmpty()) {
      messageText = "test";
    }

    message.publishNewClientEvent(messageText);

    return ResponseEntity.ok().build();
  }
}
