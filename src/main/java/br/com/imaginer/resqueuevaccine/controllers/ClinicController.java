package br.com.imaginer.resqueuevaccine.controllers;

import br.com.imaginer.resqueuevaccine.dto.ClinicRequest;
import br.com.imaginer.resqueuevaccine.exception.UnauthorizedUser;
import br.com.imaginer.resqueuevaccine.models.Clinic;
import br.com.imaginer.resqueuevaccine.services.ClinicService;
import br.com.imaginer.resqueuevaccine.utils.GetTokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/vaccine/clinic")
@Tag(name = "Clinic", description = "Clinic Control status (Replication)")
public class ClinicController {

  private final ClinicService clinicService;

  public ClinicController(ClinicService clinicService) {
    this.clinicService = clinicService;
  }

  @Operation(description = "Cria uma nova clínica associada a um usuário.",
      security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping("/create")
  public ResponseEntity<Clinic> createClinic(
      @AuthenticationPrincipal Jwt jwt,
      @RequestBody ClinicRequest body
  ) {

    UUID userId = GetTokenJWT.obtainUserId(jwt);

    if (!userId.equals(body.userId())) {
      throw new UnauthorizedUser();
    }

    Clinic createClinic = Clinic.builder()
        .clinicId(body.clinicId())
        .userId(userId)
        .active(body.active())
        .build();

    return ResponseEntity.status(201).body(clinicService.create(createClinic));
  }

  @Operation(description = "Atualizar o status da clínica.",
      security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping("/update/{clinicId}")
  public ResponseEntity<Clinic> updateClinic(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID clinicId,
      @RequestParam boolean status
  ) {

    UUID userId = GetTokenJWT.obtainUserId(jwt);

    return ResponseEntity.status(201).body(
        clinicService.update(clinicId, userId, status)
    );
  }

  @Operation(description = "Obter a clínica do usuário.",
      security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping()
  public ResponseEntity<Clinic> getUserClinic(
      @AuthenticationPrincipal Jwt jwt
  ) {

    UUID userId = GetTokenJWT.obtainUserId(jwt);

    return ResponseEntity.status(200).body(
        clinicService.findByUserId(userId)
    );
  }

}
