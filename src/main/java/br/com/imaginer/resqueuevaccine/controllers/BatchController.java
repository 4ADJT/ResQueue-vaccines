package br.com.imaginer.resqueuevaccine.controllers;

import java.util.List;
import java.util.UUID;

import br.com.imaginer.resqueuevaccine.ms.ClientPublisherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.services.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RestController
@RequestMapping(path = "/vaccine")
@Tag(name = "Batch", description = "Batch REST operations")
public class BatchController {

  private final ClientPublisherService message;
  private final BatchService batchService;

  public BatchController(BatchService batchService, ClientPublisherService message) {
    this.batchService = batchService;
    this.message = message;
  }

  @Operation(summary = "Get all batches", security = { @SecurityRequirement(name = "bearer-key") })
  @GetMapping
  public ResponseEntity<List<Batch>> getAll(
      @AuthenticationPrincipal Jwt jwt
  ) {
    UUID userId = UUID.fromString(jwt.getSubject());
    String userEmailFromToken = jwt.getClaim("email");

    log.info("Usuário autenticado: " + userId + " - " + userEmailFromToken);

    return ResponseEntity.ok(batchService.findAll());
  }

  @Operation(summary = "Get batch by ID", security = { @SecurityRequirement(name = "bearer-key") })
  @GetMapping("/{id}")
  public ResponseEntity<Batch> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(batchService.findById(id));
  }

  @Operation(summary = "Create new batch", security = { @SecurityRequirement(name = "bearer-key") })
  @PostMapping
  public ResponseEntity<Batch> create(@RequestBody Batch batch) {
    return ResponseEntity.ok(batchService.create(batch));
  }

  @Operation(summary = "Update existing batch", security = { @SecurityRequirement(name = "bearer-key") })
  @PutMapping("/{id}")
  public ResponseEntity<Batch> update(@PathVariable UUID id, @RequestBody Batch batchData) {
    return ResponseEntity.ok(batchService.update(id, batchData));
  }

  @Operation(summary = "Delete batch by ID", security = { @SecurityRequirement(name = "bearer-key") })
  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    batchService.delete(id);
  }

  @Operation(summary = "Use batch", security = { @SecurityRequirement(name = "bearer-key") })
  @PostMapping("/{id}/use")
  public ResponseEntity<String> useBatch(@PathVariable UUID id, @RequestBody int quantity) {
      return ResponseEntity.status(HttpStatus.CREATED).body(batchService.useBatch(id, quantity));
  }

  @Operation(summary = "Use message service")
  @GetMapping("/message")
  public ResponseEntity<?> sendMessage() {

    message.publishNewClientEvent("test");

    return ResponseEntity.ok().build();
  }
}
