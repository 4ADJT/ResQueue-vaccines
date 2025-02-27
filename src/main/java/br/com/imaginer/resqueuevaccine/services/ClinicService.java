package br.com.imaginer.resqueuevaccine.services;

import br.com.imaginer.resqueuevaccine.dto.ClinicRequest;
import br.com.imaginer.resqueuevaccine.models.Clinic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicService {

  Clinic create(ClinicRequest clinic);

  Clinic findByUserIdAndClinicId(UUID clinicId, UUID userId);

  Clinic deactivate(UUID clinicId, UUID userId);

  Optional<List<Clinic>> getClinicsByUser(UUID userId);

  Clinic getActiveClinicByUser(UUID userId);

}
