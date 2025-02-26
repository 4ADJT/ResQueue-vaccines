package br.com.imaginer.resqueuevaccine.services;

import br.com.imaginer.resqueuevaccine.models.Clinic;

import java.util.UUID;

public interface ClinicService {

  Clinic create(Clinic clinic);

  Clinic findByUserId(UUID userId);

  Clinic update(UUID clinicId, UUID userId, boolean active);

}
