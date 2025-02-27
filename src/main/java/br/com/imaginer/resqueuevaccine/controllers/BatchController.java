package br.com.imaginer.resqueuevaccine.controllers;

import br.com.imaginer.resqueuevaccine.dto.BatchRequest;
import br.com.imaginer.resqueuevaccine.exception.ClinicNotFoundException;
import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.models.Clinic;
import br.com.imaginer.resqueuevaccine.services.BatchService;
import br.com.imaginer.resqueuevaccine.services.BatchServiceImpl;
import br.com.imaginer.resqueuevaccine.services.ClinicService;
import br.com.imaginer.resqueuevaccine.utils.GetTokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/vaccine")
@Tag(name = "Batch", description = "Batch REST operations")
public class BatchController {

  private final BatchService batchService;

  public BatchController(BatchServiceImpl batchService
  ) {
    this.batchService = batchService;
  }

  @Operation(summary = "Get all batches", security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<Page<Batch>> getAll(
      @PageableDefault(size = 10, sort = "expiryDate", direction = Sort.Direction.ASC) Pageable pageable,
      @AuthenticationPrincipal Jwt jwt
  ) {
    UUID userId = GetTokenJWT.obtainUserId(jwt);

//    Clinic clinicByUser = clinicService.findByUserId(userId);
//
//    if (clinicByUser == null) {
//      throw new ClinicNotFoundException();
//    }

    return ResponseEntity.ok(batchService.findAllByClinicId(userId, pageable));
  }

  @Operation(summary = "Get batch by ID", security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping("/{batchId}")
  public ResponseEntity<Batch> getById(@PathVariable UUID batchId,
                                       @AuthenticationPrincipal Jwt jwt
  ) {
    UUID userId = GetTokenJWT.obtainUserId(jwt);
//
//    Clinic clinicByUser = clinicService.findByUserIdAndClinicId(userId);
//
//    if (clinicByUser == null) {
//      throw new ClinicNotFoundException();
//    }

    return ResponseEntity.ok(batchService.findById(batchId));
  }

  @Operation(summary = "Create new batch", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<Batch> create(@RequestBody BatchRequest batch,
                                      @AuthenticationPrincipal Jwt jwt
  ) {
    UUID userId = GetTokenJWT.obtainUserId(jwt);
    return ResponseEntity.status(201)
        .body(batchService.create(userId, batch));
  }

  @Operation(summary = "Update existing batch", security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping("/{id}")
  public ResponseEntity<Batch> update(@PathVariable UUID id,
                                      @RequestBody BatchRequest batchData,
                                      @AuthenticationPrincipal Jwt jwt
  ) {
    return null; // ResponseEntity.ok(batchService.update(id, batchData));
  }

  @Operation(summary = "Delete batch by ID", security = {@SecurityRequirement(name = "bearer-key")})
  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    batchService.delete(id);
  }

  @Operation(summary = "Use batch", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping("/{id}/use")
  public ResponseEntity<String> useBatch(@PathVariable UUID id,
                                         @RequestBody int quantity,
                                         @AuthenticationPrincipal Jwt jwt
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(batchService.useBatch(id, quantity));
  }


}
